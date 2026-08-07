/**
 * In-memory data store for demo purposes.
 * In production, replace with a real database (MongoDB, PostgreSQL, etc.)
 */

// Pre-seeded users matching the Android Room database seed
const users = [
    {
        id:          'user-emp-001',
        name:        'John Doe',
        email:       'john.doe@leaveflow.com',
        role:        'EMPLOYEE',
        department:  'Engineering',
        employee_id: 'EMP001',
        manager_id:  'user-mgr-001'
    },
    {
        id:          'user-emp-002',
        name:        'Alice Perera',
        email:       'alice.perera@leaveflow.com',
        role:        'EMPLOYEE',
        department:  'Finance',
        employee_id: 'EMP002',
        manager_id:  'user-mgr-001'
    },
    {
        id:          'user-mgr-001',
        name:        'Sarah Smith',
        email:       'sarah.smith@leaveflow.com',
        role:        'MANAGER',
        department:  'Engineering',
        employee_id: 'MGR001'
    },
    {
        id:          'user-hr-001',
        name:        'Admin HR',
        email:       'admin.hr@leaveflow.com',
        role:        'HR',
        department:  'Human Resources',
        employee_id: 'HR001'
    }
];

// Leave requests (populated as the app syncs)
const leaveRequests = [
    {
        id:             'req-001',
        employee_id:    'user-emp-001',
        employee_name:  'John Doe',
        department:     'Engineering',
        leave_type:     'ANNUAL',
        start_date:     '2025-06-01',
        end_date:       '2025-06-05',
        reason:         'Family vacation',
        contact_number: '+94771234567',
        number_of_days: 5,
        status:         'APPROVED',
        manager_id:     'user-mgr-001',
        manager_comment:'Approved. Enjoy your vacation!',
        created_at:     '2025-05-20T08:00:00.000Z'
    },
    {
        id:             'req-002',
        employee_id:    'user-emp-001',
        employee_name:  'John Doe',
        department:     'Engineering',
        leave_type:     'CASUAL',
        start_date:     '2025-07-10',
        end_date:       '2025-07-11',
        reason:         'Personal matters',
        contact_number: '+94771234567',
        number_of_days: 2,
        status:         'PENDING',
        created_at:     '2025-07-05T10:00:00.000Z'
    }
];

module.exports = { users, leaveRequests };
