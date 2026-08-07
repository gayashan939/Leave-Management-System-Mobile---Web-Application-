/**
 * LeaveFlow Demo REST API – Node.js / Express
 * Run: npm install && node server.js
 * Listens on http://localhost:3000
 *
 * Android emulator reaches this via http://10.0.2.2:3000
 */

const express    = require('express');
const bodyParser = require('body-parser');
const cors       = require('cors');
const authRouter  = require('./routes/auth');
const leavesRouter = require('./routes/leaves');

const app  = express();
const PORT = process.env.PORT || 3000;

// ── Middleware ────────────────────────────────────────────────────────────────
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// ── Request logging ───────────────────────────────────────────────────────────
app.use((req, _res, next) => {
    console.log(`[${new Date().toISOString()}] ${req.method} ${req.url}`);
    next();
});

// ── Routes ────────────────────────────────────────────────────────────────────
app.use('/api/auth',   authRouter);
app.use('/api/leaves', leavesRouter);

// Health check
app.get('/health', (_req, res) => {
    res.json({ status: 'OK', service: 'LeaveFlow API', timestamp: new Date().toISOString() });
});

// Balance endpoint (stub – returns seeded data for demo employee)
app.get('/api/balances/:employeeId', (req, res) => {
    res.json({
        employee_id:    req.params.employeeId,
        annual_total:   20,
        annual_used:    5,
        casual_total:   10,
        casual_used:    2,
        medical_total:  14,
        medical_used:   0,
        nopay_used:     0
    });
});

// ── 404 handler ───────────────────────────────────────────────────────────────
app.use((_req, res) => {
    res.status(404).json({ success: false, message: 'Endpoint not found' });
});

// ── Error handler ─────────────────────────────────────────────────────────────
app.use((err, _req, res, _next) => {
    console.error('Server error:', err.message);
    res.status(500).json({ success: false, message: 'Internal server error' });
});

// ── Start ─────────────────────────────────────────────────────────────────────
app.listen(PORT, () => {
    console.log(`\n🚀 LeaveFlow API running on http://localhost:${PORT}`);
    console.log(`   Android emulator: http://10.0.2.2:${PORT}`);
    console.log(`   Health check: http://localhost:${PORT}/health\n`);
});
