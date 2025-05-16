const express = require('express');
const cors = require('cors');
const multer = require('multer');
const path = require('path');
const fs = require('fs');

const app = express();
const port = 8080;

// Middleware
app.use(cors());
app.use(express.json());

// Configuración de almacenamiento de imágenes
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        const uploadDir = 'uploads';
        if (!fs.existsSync(uploadDir)) {
            fs.mkdirSync(uploadDir);
        }
        cb(null, uploadDir);
    },
    filename: function (req, file, cb) {
        cb(null, Date.now() + path.extname(file.originalname));
    }
});

const upload = multer({ storage: storage });

// Almacenamiento en memoria (simulando base de datos)
let reports = [];

// Middleware de autenticación
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (token === 'a0f4dcad-5903-482f-8982-88ec8bc6156e') {
        next();
    } else {
        res.status(401).json({ error: 'Token no válido' });
    }
};

// Endpoint para enviar reporte
app.post('/apireporte/reporte.php', authenticateToken, async (req, res) => {
    try {
        const report = {
            id: Date.now(),
            ...req.body,
            fecha: new Date().toISOString(),
            estado: 'PENDIENTE'
        };

        // Si hay una imagen en base64, guardarla
        if (report.imagen && report.imagen.startsWith('data:image')) {
            const base64Data = report.imagen.replace(/^data:image\/\w+;base64,/, '');
            const buffer = Buffer.from(base64Data, 'base64');
            const filename = `uploads/${report.id}.jpg`;
            fs.writeFileSync(filename, buffer);
            report.imagen = filename;
        }

        reports.push(report);

        res.json({
            success: true,
            message: 'Reporte recibido correctamente',
            reportId: report.id
        });
    } catch (error) {
        res.status(500).json({
            success: false,
            message: 'Error al procesar el reporte',
            error: error.message
        });
    }
});

// Endpoint para consultar reportes
app.get('/apireporte/reportes.php', authenticateToken, (req, res) => {
    res.json({
        success: true,
        reports: reports
    });
});

// Endpoint para consultar un reporte específico
app.get('/apireporte/reporte.php/:id', authenticateToken, (req, res) => {
    const report = reports.find(r => r.id === parseInt(req.params.id));
    if (report) {
        res.json({
            success: true,
            report: report
        });
    } else {
        res.status(404).json({
            success: false,
            message: 'Reporte no encontrado'
        });
    }
});

// Iniciar servidor
app.listen(port, () => {
    console.log(`Servidor corriendo en http://localhost:${port}`);
}); 