/**
 * Auth routes – /api/auth
 */
const express = require('express');
const router  = express.Router();
const { users } = require('../data/store');

/**
 * POST /api/auth/login
 * Body: { email, password }
 *
 * NOTE: Passwords are hashed with BCrypt in the Android app.
 * This demo server accepts any password for the seeded users to simplify
 * the demo – in production, verify the BCrypt hash here.
 */
router.post('/login', (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({ success: false, message: 'Email and password are required.' });
    }

    const user = users.find(u => u.email.toLowerCase() === email.toLowerCase());
    if (!user) {
        return res.status(401).json({ success: false, message: 'No account found with this email address.' });
    }

    // Demo: accept any password for seeded users
    // In production: const valid = bcrypt.compareSync(password, user.passwordHash);
    const demoPassword = 'Pass@1234';
    if (password !== demoPassword) {
        return res.status(401).json({ success: false, message: 'Incorrect password.' });
    }

    res.json({
        success: true,
        message: 'Login successful',
        token: `demo-token-${user.id}-${Date.now()}`,
        user: {
            id:          user.id,
            name:        user.name,
            email:       user.email,
            role:        user.role,
            department:  user.department,
            employee_id: user.employee_id
        }
    });
});

module.exports = router;
