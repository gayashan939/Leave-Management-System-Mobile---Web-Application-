package com.leaveflow.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leaveflow.app.data.local.dao.LeaveBalanceDao
import com.leaveflow.app.data.local.dao.LeaveRequestDao
import com.leaveflow.app.data.local.dao.SyncQueueDao
import com.leaveflow.app.data.local.dao.UserDao
import com.leaveflow.app.data.local.entity.LeaveBalanceEntity
import com.leaveflow.app.data.local.entity.LeaveRequestEntity
import com.leaveflow.app.data.local.entity.SyncQueueEntity
import com.leaveflow.app.data.local.entity.UserEntity
import com.leaveflow.app.util.PasswordUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Database(
    entities = [
        UserEntity::class,
        LeaveRequestEntity::class,
        LeaveBalanceEntity::class,
        SyncQueueEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun leaveRequestDao(): LeaveRequestDao
    abstract fun leaveBalanceDao(): LeaveBalanceDao
    abstract fun syncQueueDao(): SyncQueueDao

    companion object {
        const val DATABASE_NAME = "leaveflow_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(SeedCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * Pre-populates the database with demo accounts on first creation.
     * Demo credentials:
     *   Employee  → john.doe@leaveflow.com   / Pass@1234
     *   Manager   → sarah.smith@leaveflow.com / Pass@1234
     *   HR Admin  → admin.hr@leaveflow.com    / Pass@1234
     */
    private class SeedCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val database = getInstance(context)
                seedUsers(database)
            }
        }

        private suspend fun seedUsers(db: AppDatabase) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // ── Users ─────────────────────────────────────────────────────────
            val managerId   = "user-mgr-001"
            val employeeId1 = "user-emp-001"
            val employeeId2 = "user-emp-002"
            val hrId        = "user-hr-001"

            val passwordHash = PasswordUtil.hashPassword("Pass@1234")

            val users = listOf(
                UserEntity(
                    id         = employeeId1,
                    name       = "John Doe",
                    email      = "john.doe@leaveflow.com",
                    passwordHash = passwordHash,
                    role       = "EMPLOYEE",
                    department = "Engineering",
                    employeeId = "EMP001",
                    managerId  = managerId
                ),
                UserEntity(
                    id         = employeeId2,
                    name       = "Alice Perera",
                    email      = "alice.perera@leaveflow.com",
                    passwordHash = passwordHash,
                    role       = "EMPLOYEE",
                    department = "Finance",
                    employeeId = "EMP002",
                    managerId  = managerId
                ),
                UserEntity(
                    id         = managerId,
                    name       = "Sarah Smith",
                    email      = "sarah.smith@leaveflow.com",
                    passwordHash = passwordHash,
                    role       = "MANAGER",
                    department = "Engineering",
                    employeeId = "MGR001"
                ),
                UserEntity(
                    id         = hrId,
                    name       = "Admin HR",
                    email      = "admin.hr@leaveflow.com",
                    passwordHash = passwordHash,
                    role       = "HR",
                    department = "Human Resources",
                    employeeId = "HR001"
                )
            )
            db.userDao().insertUsers(users)

            // ── Leave Balances ─────────────────────────────────────────────────
            val balances = listOf(
                LeaveBalanceEntity(
                    employeeId   = employeeId1,
                    annualTotal  = 20, annualUsed  = 5,  annualPending  = 0,
                    casualTotal  = 10, casualUsed  = 2,  casualPending  = 0,
                    medicalTotal = 14, medicalUsed = 0,  medicalPending = 0,
                    noPayUsed    = 0
                ),
                LeaveBalanceEntity(
                    employeeId   = employeeId2,
                    annualTotal  = 20, annualUsed  = 3,  annualPending  = 2,
                    casualTotal  = 10, casualUsed  = 1,  casualPending  = 0,
                    medicalTotal = 14, medicalUsed = 3,  medicalPending = 0,
                    noPayUsed    = 0
                )
            )
            db.leaveBalanceDao().insertBalances(balances)

            // ── Sample Leave Requests ─────────────────────────────────────────
            val requests = listOf(
                LeaveRequestEntity(
                    id            = "req-001",
                    employeeId    = employeeId1,
                    employeeName  = "John Doe",
                    department    = "Engineering",
                    leaveType     = "ANNUAL",
                    startDate     = "2025-06-01",
                    endDate       = "2025-06-05",
                    reason        = "Family vacation",
                    contactNumber = "+94771234567",
                    numberOfDays  = 5,
                    status        = "APPROVED",
                    syncStatus    = "SYNCED",
                    managerId     = managerId,
                    managerComment = "Approved. Enjoy your vacation!",
                    createdAt     = sdf.parse("2025-05-20")!!.time
                ),
                LeaveRequestEntity(
                    id            = "req-002",
                    employeeId    = employeeId1,
                    employeeName  = "John Doe",
                    department    = "Engineering",
                    leaveType     = "CASUAL",
                    startDate     = "2025-07-10",
                    endDate       = "2025-07-11",
                    reason        = "Personal matters",
                    contactNumber = "+94771234567",
                    numberOfDays  = 2,
                    status        = "PENDING",
                    syncStatus    = "SYNCED",
                    createdAt     = sdf.parse("2025-07-05")!!.time
                ),
                LeaveRequestEntity(
                    id            = "req-003",
                    employeeId    = employeeId2,
                    employeeName  = "Alice Perera",
                    department    = "Finance",
                    leaveType     = "ANNUAL",
                    startDate     = "2025-08-01",
                    endDate       = "2025-08-02",
                    reason        = "Home renovation",
                    contactNumber = "+94779876543",
                    numberOfDays  = 2,
                    status        = "PENDING",
                    syncStatus    = "SYNCED",
                    createdAt     = sdf.parse("2025-07-28")!!.time
                ),
                LeaveRequestEntity(
                    id            = "req-004",
                    employeeId    = employeeId1,
                    employeeName  = "John Doe",
                    department    = "Engineering",
                    leaveType     = "MEDICAL",
                    startDate     = "2025-04-15",
                    endDate       = "2025-04-15",
                    reason        = "Doctor appointment",
                    contactNumber = "+94771234567",
                    numberOfDays  = 1,
                    status        = "REJECTED",
                    syncStatus    = "SYNCED",
                    managerId     = managerId,
                    managerComment = "Insufficient documentation provided.",
                    createdAt     = sdf.parse("2025-04-10")!!.time
                )
            )
            db.leaveRequestDao().insertLeaveRequests(requests)
        }
    }
}
