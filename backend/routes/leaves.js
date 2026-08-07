/**
 * Leave request routes – /api/leaves
 */
const express = require('express');
const router  = express.Router();
const { v4: uuidv4 } = require('uuid');
const { leaveRequests } = require('../data/store');

// ── GET /api/leaves ───────────────────────────────────────────────────────────
router.get('/', (_req, res) => {
    res.json(leaveRequests);
});

// ── GET /api/leaves/summary ───────────────────────────────────────────────────
router.get('/summary', (_req, res) => {
    const total    = leaveRequests.length;
    const approved = leaveRequests.filter(r => r.status === 'APPROVED').length;
    const pending  = leaveRequests.filter(r => r.status === 'PENDING').length;
    const rejected = leaveRequests.filter(r => r.status === 'REJECTED').length;
    res.json({ total, approved, pending, rejected });
});

// ── GET /api/leaves/pending ───────────────────────────────────────────────────
router.get('/pending', (_req, res) => {
    res.json(leaveRequests.filter(r => r.status === 'PENDING'));
});

// ── GET /api/leaves/employee/:employeeId ──────────────────────────────────────
router.get('/employee/:employeeId', (req, res) => {
    const requests = leaveRequests.filter(r => r.employee_id === req.params.employeeId);
    res.json(requests);
});

// ── GET /api/leaves/:id ───────────────────────────────────────────────────────
router.get('/:id', (req, res) => {
    const request = leaveRequests.find(r => r.id === req.params.id);
    if (!request) return res.status(404).json({ success: false, message: 'Leave request not found.' });
    res.json(request);
});

// ── POST /api/leaves ──────────────────────────────────────────────────────────
router.post('/', (req, res) => {
    const {
        id, employee_id, employee_name, department,
        leave_type, start_date, end_date, reason,
        contact_number, number_of_days, status,
        latitude, longitude
    } = req.body;

    // Validate required fields
    if (!employee_id || !leave_type || !start_date || !end_date || !reason) {
        return res.status(400).json({ success: false, message: 'Missing required fields.' });
    }

    // Prevent duplicate submissions (idempotency via unique ID)
    const requestId = id || uuidv4();
    const existing  = leaveRequests.find(r => r.id === requestId);
    if (existing) {
        console.log(`Duplicate request rejected: ${requestId}`);
        return res.status(409).json({ success: false, message: 'Duplicate request. Already submitted.' });
    }

    const newRequest = {
        id:             requestId,
        employee_id:    employee_id,
        employee_name:  employee_name || 'Unknown',
        department:     department    || 'Unknown',
        leave_type:     leave_type,
        start_date:     start_date,
        end_date:       end_date,
        reason:         reason,
        contact_number: contact_number || '',
        number_of_days: number_of_days || 0,
        status:         status         || 'PENDING',
        latitude:       latitude       || null,
        longitude:      longitude      || null,
        manager_id:     null,
        manager_comment: null,
        created_at:     new Date().toISOString()
    };

    leaveRequests.push(newRequest);
    console.log(`New leave request created: ${requestId} for ${employee_name}`);
    res.status(201).json(newRequest);
});

// ── PUT /api/leaves/:id/status ────────────────────────────────────────────────
router.put('/:id/status', (req, res) => {
    const request = leaveRequests.find(r => r.id === req.params.id);
    if (!request) return res.status(404).json({ success: false, message: 'Leave request not found.' });

    const { status, manager_id, manager_comment } = req.body;
    if (!status || !['APPROVED', 'REJECTED'].includes(status)) {
        return res.status(400).json({ success: false, message: 'Status must be APPROVED or REJECTED.' });
    }

    request.status          = status;
    request.manager_id      = manager_id     || null;
    request.manager_comment = manager_comment || null;
    request.updated_at      = new Date().toISOString();

    console.log(`Leave request ${req.params.id} ${status.toLowerCase()} by ${manager_id}`);
    res.json(request);
});

// ── DELETE /api/leaves/:id ────────────────────────────────────────────────────
router.delete('/:id', (req, res) => {
    const index = leaveRequests.findIndex(r => r.id === req.params.id);
    if (index === -1) return res.status(404).json({ success: false, message: 'Leave request not found.' });

    leaveRequests.splice(index, 1);
    console.log(`Leave request ${req.params.id} deleted.`);
    res.json({ success: true, message: 'Leave request deleted.' });
});

module.exports = router;
