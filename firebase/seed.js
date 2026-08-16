'use strict';

const { applicationDefault, initializeApp } = require('firebase-admin/app');
const { getAuth } = require('firebase-admin/auth');
const { FieldValue, Timestamp, getFirestore } = require('firebase-admin/firestore');

initializeApp({ credential: applicationDefault() });

const auth = getAuth();
const db = getFirestore();
const demoPassword = process.env.LEAVEFLOW_DEMO_PASSWORD || 'Pass@1234';

const users = [
  {
    uid: 'user-emp-001', name: 'John Doe', email: 'john.doe@leaveflow.com',
    role: 'EMPLOYEE', department: 'Engineering', employeeId: 'EMP001', managerId: 'user-mgr-001'
  },
  {
    uid: 'user-emp-002', name: 'Alice Perera', email: 'alice.perera@leaveflow.com',
    role: 'EMPLOYEE', department: 'Finance', employeeId: 'EMP002', managerId: 'user-mgr-001'
  },
  {
    uid: 'user-mgr-001', name: 'Sarah Smith', email: 'sarah.smith@leaveflow.com',
    role: 'MANAGER', department: 'Engineering', employeeId: 'MGR001', managerId: null
  },
  {
    uid: 'user-hr-001', name: 'Admin HR', email: 'admin.hr@leaveflow.com',
    role: 'HR', department: 'Human Resources', employeeId: 'HR001', managerId: null
  }
];

const balances = [
  {
    employeeId: 'user-emp-001', annualTotal: 20, annualUsed: 5, annualPending: 0,
    casualTotal: 10, casualUsed: 2, casualPending: 2,
    medicalTotal: 14, medicalUsed: 0, medicalPending: 0, noPayUsed: 0, noPayPending: 0
  },
  {
    employeeId: 'user-emp-002', annualTotal: 20, annualUsed: 3, annualPending: 2,
    casualTotal: 10, casualUsed: 1, casualPending: 0,
    medicalTotal: 14, medicalUsed: 3, medicalPending: 0, noPayUsed: 0, noPayPending: 0
  }
];

const leaves = [
  {
    id: 'req-001', employeeId: 'user-emp-001', employeeName: 'John Doe', department: 'Engineering',
    leaveType: 'ANNUAL', startDate: '2025-06-01', endDate: '2025-06-05', numberOfDays: 5,
    reason: 'Family vacation', contactNumber: '+94771234567', status: 'APPROVED',
    managerId: 'user-mgr-001', managerComment: 'Approved. Enjoy your vacation!', createdAt: '2025-05-20'
  },
  {
    id: 'req-002', employeeId: 'user-emp-001', employeeName: 'John Doe', department: 'Engineering',
    leaveType: 'CASUAL', startDate: '2025-07-10', endDate: '2025-07-11', numberOfDays: 2,
    reason: 'Personal matters', contactNumber: '+94771234567', status: 'PENDING',
    managerId: null, managerComment: null, createdAt: '2025-07-05'
  },
  {
    id: 'req-003', employeeId: 'user-emp-002', employeeName: 'Alice Perera', department: 'Finance',
    leaveType: 'ANNUAL', startDate: '2025-08-01', endDate: '2025-08-02', numberOfDays: 2,
    reason: 'Home renovation', contactNumber: '+94779876543', status: 'PENDING',
    managerId: null, managerComment: null, createdAt: '2025-07-28'
  },
  {
    id: 'req-004', employeeId: 'user-emp-001', employeeName: 'John Doe', department: 'Engineering',
    leaveType: 'MEDICAL', startDate: '2025-04-15', endDate: '2025-04-15', numberOfDays: 1,
    reason: 'Doctor appointment', contactNumber: '+94771234567', status: 'REJECTED',
    managerId: 'user-mgr-001', managerComment: 'Insufficient documentation provided.', createdAt: '2025-04-10'
  }
];

async function upsertAuthUser(user) {
  const values = { email: user.email, password: demoPassword, displayName: user.name, emailVerified: true };
  try {
    await auth.getUser(user.uid);
    await auth.updateUser(user.uid, values);
  } catch (error) {
    if (error.code !== 'auth/user-not-found') throw error;
    await auth.createUser({ uid: user.uid, ...values });
  }
}

async function seed() {
  await Promise.all(users.map(upsertAuthUser));

  const batch = db.batch();
  for (const user of users) {
    const { uid, ...profile } = user;
    batch.set(db.collection('users').doc(uid), {
      ...profile,
      active: true,
      createdAt: FieldValue.serverTimestamp(),
      updatedAt: FieldValue.serverTimestamp()
    }, { merge: true });
  }
  for (const balance of balances) {
    batch.set(db.collection('leaveBalances').doc(balance.employeeId), {
      ...balance,
      updatedAt: FieldValue.serverTimestamp()
    }, { merge: true });
  }
  for (const leave of leaves) {
    const { id, createdAt, ...data } = leave;
    const timestamp = Timestamp.fromDate(new Date(`${createdAt}T00:00:00.000Z`));
    batch.set(db.collection('leaveRequests').doc(id), {
      ...data,
      latitude: null,
      longitude: null,
      photoUrl: null,
      attachmentPath: null,
      deleted: false,
      revision: 1,
      createdAt: timestamp,
      updatedAt: timestamp
    }, { merge: true });
  }
  await batch.commit();
  console.log(`Seeded ${users.length} users, ${balances.length} balances, and ${leaves.length} leave requests.`);
}

seed().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
