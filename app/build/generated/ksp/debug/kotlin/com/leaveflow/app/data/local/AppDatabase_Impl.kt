package com.leaveflow.app.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.leaveflow.app.`data`.local.dao.BlockedDateDao
import com.leaveflow.app.`data`.local.dao.BlockedDateDao_Impl
import com.leaveflow.app.`data`.local.dao.LeaveBalanceDao
import com.leaveflow.app.`data`.local.dao.LeaveBalanceDao_Impl
import com.leaveflow.app.`data`.local.dao.LeaveRequestDao
import com.leaveflow.app.`data`.local.dao.LeaveRequestDao_Impl
import com.leaveflow.app.`data`.local.dao.SyncQueueDao
import com.leaveflow.app.`data`.local.dao.SyncQueueDao_Impl
import com.leaveflow.app.`data`.local.dao.UserDao
import com.leaveflow.app.`data`.local.dao.UserDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _userDao: Lazy<UserDao> = lazy {
    UserDao_Impl(this)
  }

  private val _leaveRequestDao: Lazy<LeaveRequestDao> = lazy {
    LeaveRequestDao_Impl(this)
  }

  private val _leaveBalanceDao: Lazy<LeaveBalanceDao> = lazy {
    LeaveBalanceDao_Impl(this)
  }

  private val _syncQueueDao: Lazy<SyncQueueDao> = lazy {
    SyncQueueDao_Impl(this)
  }

  private val _blockedDateDao: Lazy<BlockedDateDao> = lazy {
    BlockedDateDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(3, "b60e1adb3fda04649c145cb4c2dea44c", "76b626110fc52c203a3d49401d78f762") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, `role` TEXT NOT NULL, `department` TEXT NOT NULL, `employeeId` TEXT NOT NULL, `managerId` TEXT, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `leave_requests` (`id` TEXT NOT NULL, `employeeId` TEXT NOT NULL, `employeeName` TEXT NOT NULL, `department` TEXT NOT NULL, `leaveType` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL, `reason` TEXT NOT NULL, `contactNumber` TEXT NOT NULL, `numberOfDays` INTEGER NOT NULL, `status` TEXT NOT NULL, `photoPath` TEXT, `latitude` REAL, `longitude` REAL, `syncStatus` TEXT NOT NULL, `managerId` TEXT, `managerComment` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`employeeId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_leave_requests_employeeId` ON `leave_requests` (`employeeId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `leave_balances` (`employeeId` TEXT NOT NULL, `annualTotal` INTEGER NOT NULL, `annualUsed` INTEGER NOT NULL, `annualPending` INTEGER NOT NULL, `casualTotal` INTEGER NOT NULL, `casualUsed` INTEGER NOT NULL, `casualPending` INTEGER NOT NULL, `medicalTotal` INTEGER NOT NULL, `medicalUsed` INTEGER NOT NULL, `medicalPending` INTEGER NOT NULL, `noPayUsed` INTEGER NOT NULL, `noPayPending` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, PRIMARY KEY(`employeeId`), FOREIGN KEY(`employeeId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `sync_queue` (`id` TEXT NOT NULL, `requestId` TEXT NOT NULL, `operation` TEXT NOT NULL, `payload` TEXT NOT NULL, `retryCount` INTEGER NOT NULL, `lastAttempt` INTEGER, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `leave_blocked_dates` (`id` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL, `reason` TEXT NOT NULL, `createdBy` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b60e1adb3fda04649c145cb4c2dea44c')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `users`")
        connection.execSQL("DROP TABLE IF EXISTS `leave_requests`")
        connection.execSQL("DROP TABLE IF EXISTS `leave_balances`")
        connection.execSQL("DROP TABLE IF EXISTS `sync_queue`")
        connection.execSQL("DROP TABLE IF EXISTS `leave_blocked_dates`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsUsers: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUsers.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("email", TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("passwordHash", TableInfo.Column("passwordHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("role", TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("department", TableInfo.Column("department", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("employeeId", TableInfo.Column("employeeId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("managerId", TableInfo.Column("managerId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUsers: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUsers: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUsers: TableInfo = TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers)
        val _existingUsers: TableInfo = read(connection, "users")
        if (!_infoUsers.equals(_existingUsers)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |users(com.leaveflow.app.data.local.entity.UserEntity).
              | Expected:
              |""".trimMargin() + _infoUsers + """
              |
              | Found:
              |""".trimMargin() + _existingUsers)
        }
        val _columnsLeaveRequests: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLeaveRequests.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("employeeId", TableInfo.Column("employeeId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("employeeName", TableInfo.Column("employeeName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("department", TableInfo.Column("department", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("leaveType", TableInfo.Column("leaveType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("startDate", TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("endDate", TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("reason", TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("contactNumber", TableInfo.Column("contactNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("numberOfDays", TableInfo.Column("numberOfDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("status", TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("photoPath", TableInfo.Column("photoPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("latitude", TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("longitude", TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("managerId", TableInfo.Column("managerId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("managerComment", TableInfo.Column("managerComment", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLeaveRequests: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysLeaveRequests.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", listOf("employeeId"), listOf("id")))
        val _indicesLeaveRequests: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesLeaveRequests.add(TableInfo.Index("index_leave_requests_employeeId", false, listOf("employeeId"), listOf("ASC")))
        val _infoLeaveRequests: TableInfo = TableInfo("leave_requests", _columnsLeaveRequests, _foreignKeysLeaveRequests, _indicesLeaveRequests)
        val _existingLeaveRequests: TableInfo = read(connection, "leave_requests")
        if (!_infoLeaveRequests.equals(_existingLeaveRequests)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |leave_requests(com.leaveflow.app.data.local.entity.LeaveRequestEntity).
              | Expected:
              |""".trimMargin() + _infoLeaveRequests + """
              |
              | Found:
              |""".trimMargin() + _existingLeaveRequests)
        }
        val _columnsLeaveBalances: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLeaveBalances.put("employeeId", TableInfo.Column("employeeId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("annualTotal", TableInfo.Column("annualTotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("annualUsed", TableInfo.Column("annualUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("annualPending", TableInfo.Column("annualPending", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("casualTotal", TableInfo.Column("casualTotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("casualUsed", TableInfo.Column("casualUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("casualPending", TableInfo.Column("casualPending", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("medicalTotal", TableInfo.Column("medicalTotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("medicalUsed", TableInfo.Column("medicalUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("medicalPending", TableInfo.Column("medicalPending", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("noPayUsed", TableInfo.Column("noPayUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("noPayPending", TableInfo.Column("noPayPending", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLeaveBalances: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysLeaveBalances.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", listOf("employeeId"), listOf("id")))
        val _indicesLeaveBalances: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLeaveBalances: TableInfo = TableInfo("leave_balances", _columnsLeaveBalances, _foreignKeysLeaveBalances, _indicesLeaveBalances)
        val _existingLeaveBalances: TableInfo = read(connection, "leave_balances")
        if (!_infoLeaveBalances.equals(_existingLeaveBalances)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |leave_balances(com.leaveflow.app.data.local.entity.LeaveBalanceEntity).
              | Expected:
              |""".trimMargin() + _infoLeaveBalances + """
              |
              | Found:
              |""".trimMargin() + _existingLeaveBalances)
        }
        val _columnsSyncQueue: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSyncQueue.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("requestId", TableInfo.Column("requestId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("operation", TableInfo.Column("operation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("payload", TableInfo.Column("payload", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("retryCount", TableInfo.Column("retryCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("lastAttempt", TableInfo.Column("lastAttempt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("status", TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSyncQueue: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSyncQueue: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSyncQueue: TableInfo = TableInfo("sync_queue", _columnsSyncQueue, _foreignKeysSyncQueue, _indicesSyncQueue)
        val _existingSyncQueue: TableInfo = read(connection, "sync_queue")
        if (!_infoSyncQueue.equals(_existingSyncQueue)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |sync_queue(com.leaveflow.app.data.local.entity.SyncQueueEntity).
              | Expected:
              |""".trimMargin() + _infoSyncQueue + """
              |
              | Found:
              |""".trimMargin() + _existingSyncQueue)
        }
        val _columnsLeaveBlockedDates: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLeaveBlockedDates.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBlockedDates.put("startDate", TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBlockedDates.put("endDate", TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBlockedDates.put("reason", TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBlockedDates.put("createdBy", TableInfo.Column("createdBy", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBlockedDates.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLeaveBlockedDates: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLeaveBlockedDates: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLeaveBlockedDates: TableInfo = TableInfo("leave_blocked_dates", _columnsLeaveBlockedDates, _foreignKeysLeaveBlockedDates, _indicesLeaveBlockedDates)
        val _existingLeaveBlockedDates: TableInfo = read(connection, "leave_blocked_dates")
        if (!_infoLeaveBlockedDates.equals(_existingLeaveBlockedDates)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |leave_blocked_dates(com.leaveflow.app.data.local.entity.BlockedDateEntity).
              | Expected:
              |""".trimMargin() + _infoLeaveBlockedDates + """
              |
              | Found:
              |""".trimMargin() + _existingLeaveBlockedDates)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "users", "leave_requests", "leave_balances", "sync_queue", "leave_blocked_dates")
  }

  public override fun clearAllTables() {
    super.performClear(true, "users", "leave_requests", "leave_balances", "sync_queue", "leave_blocked_dates")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(UserDao::class, UserDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(LeaveRequestDao::class, LeaveRequestDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(LeaveBalanceDao::class, LeaveBalanceDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SyncQueueDao::class, SyncQueueDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(BlockedDateDao::class, BlockedDateDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun userDao(): UserDao = _userDao.value

  public override fun leaveRequestDao(): LeaveRequestDao = _leaveRequestDao.value

  public override fun leaveBalanceDao(): LeaveBalanceDao = _leaveBalanceDao.value

  public override fun syncQueueDao(): SyncQueueDao = _syncQueueDao.value

  public override fun blockedDateDao(): BlockedDateDao = _blockedDateDao.value
}
