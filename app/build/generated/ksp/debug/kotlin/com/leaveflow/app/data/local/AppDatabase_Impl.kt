package com.leaveflow.app.`data`.local

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.room.RoomOpenHelper
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.leaveflow.app.`data`.local.dao.LeaveBalanceDao
import com.leaveflow.app.`data`.local.dao.LeaveBalanceDao_Impl
import com.leaveflow.app.`data`.local.dao.LeaveRequestDao
import com.leaveflow.app.`data`.local.dao.LeaveRequestDao_Impl
import com.leaveflow.app.`data`.local.dao.SyncQueueDao
import com.leaveflow.app.`data`.local.dao.SyncQueueDao_Impl
import com.leaveflow.app.`data`.local.dao.UserDao
import com.leaveflow.app.`data`.local.dao.UserDao_Impl
import java.lang.Class
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import javax.`annotation`.processing.Generated
import kotlin.Any
import kotlin.Boolean
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.Set

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION"])
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


  protected override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
    val _openCallback: SupportSQLiteOpenHelper.Callback = RoomOpenHelper(config, object :
        RoomOpenHelper.Delegate(1) {
      public override fun createAllTables(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, `role` TEXT NOT NULL, `department` TEXT NOT NULL, `employeeId` TEXT NOT NULL, `managerId` TEXT, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        db.execSQL("CREATE TABLE IF NOT EXISTS `leave_requests` (`id` TEXT NOT NULL, `employeeId` TEXT NOT NULL, `employeeName` TEXT NOT NULL, `department` TEXT NOT NULL, `leaveType` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL, `reason` TEXT NOT NULL, `contactNumber` TEXT NOT NULL, `numberOfDays` INTEGER NOT NULL, `status` TEXT NOT NULL, `photoPath` TEXT, `latitude` REAL, `longitude` REAL, `syncStatus` TEXT NOT NULL, `managerId` TEXT, `managerComment` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`employeeId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_leave_requests_employeeId` ON `leave_requests` (`employeeId`)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `leave_balances` (`employeeId` TEXT NOT NULL, `annualTotal` INTEGER NOT NULL, `annualUsed` INTEGER NOT NULL, `annualPending` INTEGER NOT NULL, `casualTotal` INTEGER NOT NULL, `casualUsed` INTEGER NOT NULL, `casualPending` INTEGER NOT NULL, `medicalTotal` INTEGER NOT NULL, `medicalUsed` INTEGER NOT NULL, `medicalPending` INTEGER NOT NULL, `noPayUsed` INTEGER NOT NULL, `noPayPending` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, PRIMARY KEY(`employeeId`), FOREIGN KEY(`employeeId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        db.execSQL("CREATE TABLE IF NOT EXISTS `sync_queue` (`id` TEXT NOT NULL, `requestId` TEXT NOT NULL, `operation` TEXT NOT NULL, `payload` TEXT NOT NULL, `retryCount` INTEGER NOT NULL, `lastAttempt` INTEGER, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '23b76c3f66d8d57360fecf3ed6ecfd6a')")
      }

      public override fun dropAllTables(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS `users`")
        db.execSQL("DROP TABLE IF EXISTS `leave_requests`")
        db.execSQL("DROP TABLE IF EXISTS `leave_balances`")
        db.execSQL("DROP TABLE IF EXISTS `sync_queue`")
        val _callbacks: List<RoomDatabase.Callback>? = mCallbacks
        if (_callbacks != null) {
          for (_callback: RoomDatabase.Callback in _callbacks) {
            _callback.onDestructiveMigration(db)
          }
        }
      }

      public override fun onCreate(db: SupportSQLiteDatabase) {
        val _callbacks: List<RoomDatabase.Callback>? = mCallbacks
        if (_callbacks != null) {
          for (_callback: RoomDatabase.Callback in _callbacks) {
            _callback.onCreate(db)
          }
        }
      }

      public override fun onOpen(db: SupportSQLiteDatabase) {
        mDatabase = db
        db.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(db)
        val _callbacks: List<RoomDatabase.Callback>? = mCallbacks
        if (_callbacks != null) {
          for (_callback: RoomDatabase.Callback in _callbacks) {
            _callback.onOpen(db)
          }
        }
      }

      public override fun onPreMigrate(db: SupportSQLiteDatabase) {
        dropFtsSyncTriggers(db)
      }

      public override fun onPostMigrate(db: SupportSQLiteDatabase) {
      }

      public override fun onValidateSchema(db: SupportSQLiteDatabase):
          RoomOpenHelper.ValidationResult {
        val _columnsUsers: HashMap<String, TableInfo.Column> = HashMap<String, TableInfo.Column>(9)
        _columnsUsers.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("email", TableInfo.Column("email", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("passwordHash", TableInfo.Column("passwordHash", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("role", TableInfo.Column("role", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("department", TableInfo.Column("department", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("employeeId", TableInfo.Column("employeeId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("managerId", TableInfo.Column("managerId", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUsers: HashSet<TableInfo.ForeignKey> = HashSet<TableInfo.ForeignKey>(0)
        val _indicesUsers: HashSet<TableInfo.Index> = HashSet<TableInfo.Index>(0)
        val _infoUsers: TableInfo = TableInfo("users", _columnsUsers, _foreignKeysUsers,
            _indicesUsers)
        val _existingUsers: TableInfo = read(db, "users")
        if (!_infoUsers.equals(_existingUsers)) {
          return RoomOpenHelper.ValidationResult(false, """
              |users(com.leaveflow.app.data.local.entity.UserEntity).
              | Expected:
              |""".trimMargin() + _infoUsers + """
              |
              | Found:
              |""".trimMargin() + _existingUsers)
        }
        val _columnsLeaveRequests: HashMap<String, TableInfo.Column> =
            HashMap<String, TableInfo.Column>(19)
        _columnsLeaveRequests.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("employeeId", TableInfo.Column("employeeId", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("employeeName", TableInfo.Column("employeeName", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("department", TableInfo.Column("department", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("leaveType", TableInfo.Column("leaveType", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("startDate", TableInfo.Column("startDate", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("endDate", TableInfo.Column("endDate", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("reason", TableInfo.Column("reason", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("contactNumber", TableInfo.Column("contactNumber", "TEXT", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("numberOfDays", TableInfo.Column("numberOfDays", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("photoPath", TableInfo.Column("photoPath", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("latitude", TableInfo.Column("latitude", "REAL", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("longitude", TableInfo.Column("longitude", "REAL", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("managerId", TableInfo.Column("managerId", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("managerComment", TableInfo.Column("managerComment", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveRequests.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLeaveRequests: HashSet<TableInfo.ForeignKey> =
            HashSet<TableInfo.ForeignKey>(1)
        _foreignKeysLeaveRequests.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION",
            listOf("employeeId"), listOf("id")))
        val _indicesLeaveRequests: HashSet<TableInfo.Index> = HashSet<TableInfo.Index>(1)
        _indicesLeaveRequests.add(TableInfo.Index("index_leave_requests_employeeId", false,
            listOf("employeeId"), listOf("ASC")))
        val _infoLeaveRequests: TableInfo = TableInfo("leave_requests", _columnsLeaveRequests,
            _foreignKeysLeaveRequests, _indicesLeaveRequests)
        val _existingLeaveRequests: TableInfo = read(db, "leave_requests")
        if (!_infoLeaveRequests.equals(_existingLeaveRequests)) {
          return RoomOpenHelper.ValidationResult(false, """
              |leave_requests(com.leaveflow.app.data.local.entity.LeaveRequestEntity).
              | Expected:
              |""".trimMargin() + _infoLeaveRequests + """
              |
              | Found:
              |""".trimMargin() + _existingLeaveRequests)
        }
        val _columnsLeaveBalances: HashMap<String, TableInfo.Column> =
            HashMap<String, TableInfo.Column>(13)
        _columnsLeaveBalances.put("employeeId", TableInfo.Column("employeeId", "TEXT", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("annualTotal", TableInfo.Column("annualTotal", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("annualUsed", TableInfo.Column("annualUsed", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("annualPending", TableInfo.Column("annualPending", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("casualTotal", TableInfo.Column("casualTotal", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("casualUsed", TableInfo.Column("casualUsed", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("casualPending", TableInfo.Column("casualPending", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("medicalTotal", TableInfo.Column("medicalTotal", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("medicalUsed", TableInfo.Column("medicalUsed", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("medicalPending", TableInfo.Column("medicalPending", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("noPayUsed", TableInfo.Column("noPayUsed", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("noPayPending", TableInfo.Column("noPayPending", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLeaveBalances.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLeaveBalances: HashSet<TableInfo.ForeignKey> =
            HashSet<TableInfo.ForeignKey>(1)
        _foreignKeysLeaveBalances.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION",
            listOf("employeeId"), listOf("id")))
        val _indicesLeaveBalances: HashSet<TableInfo.Index> = HashSet<TableInfo.Index>(0)
        val _infoLeaveBalances: TableInfo = TableInfo("leave_balances", _columnsLeaveBalances,
            _foreignKeysLeaveBalances, _indicesLeaveBalances)
        val _existingLeaveBalances: TableInfo = read(db, "leave_balances")
        if (!_infoLeaveBalances.equals(_existingLeaveBalances)) {
          return RoomOpenHelper.ValidationResult(false, """
              |leave_balances(com.leaveflow.app.data.local.entity.LeaveBalanceEntity).
              | Expected:
              |""".trimMargin() + _infoLeaveBalances + """
              |
              | Found:
              |""".trimMargin() + _existingLeaveBalances)
        }
        val _columnsSyncQueue: HashMap<String, TableInfo.Column> =
            HashMap<String, TableInfo.Column>(8)
        _columnsSyncQueue.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("requestId", TableInfo.Column("requestId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("operation", TableInfo.Column("operation", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("payload", TableInfo.Column("payload", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("retryCount", TableInfo.Column("retryCount", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("lastAttempt", TableInfo.Column("lastAttempt", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSyncQueue.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSyncQueue: HashSet<TableInfo.ForeignKey> = HashSet<TableInfo.ForeignKey>(0)
        val _indicesSyncQueue: HashSet<TableInfo.Index> = HashSet<TableInfo.Index>(0)
        val _infoSyncQueue: TableInfo = TableInfo("sync_queue", _columnsSyncQueue,
            _foreignKeysSyncQueue, _indicesSyncQueue)
        val _existingSyncQueue: TableInfo = read(db, "sync_queue")
        if (!_infoSyncQueue.equals(_existingSyncQueue)) {
          return RoomOpenHelper.ValidationResult(false, """
              |sync_queue(com.leaveflow.app.data.local.entity.SyncQueueEntity).
              | Expected:
              |""".trimMargin() + _infoSyncQueue + """
              |
              | Found:
              |""".trimMargin() + _existingSyncQueue)
        }
        return RoomOpenHelper.ValidationResult(true, null)
      }
    }, "23b76c3f66d8d57360fecf3ed6ecfd6a", "eb12662ad743965db08cfd08dc867df0")
    val _sqliteConfig: SupportSQLiteOpenHelper.Configuration =
        SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build()
    val _helper: SupportSQLiteOpenHelper = config.sqliteOpenHelperFactory.create(_sqliteConfig)
    return _helper
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: HashMap<String, String> = HashMap<String, String>(0)
    val _viewTables: HashMap<String, Set<String>> = HashMap<String, Set<String>>(0)
    return InvalidationTracker(this, _shadowTablesMap, _viewTables,
        "users","leave_requests","leave_balances","sync_queue")
  }

  public override fun clearAllTables() {
    super.assertNotMainThread()
    val _db: SupportSQLiteDatabase = super.openHelper.writableDatabase
    val _supportsDeferForeignKeys: Boolean = android.os.Build.VERSION.SDK_INT >=
        android.os.Build.VERSION_CODES.LOLLIPOP
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE")
      }
      super.beginTransaction()
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE")
      }
      _db.execSQL("DELETE FROM `users`")
      _db.execSQL("DELETE FROM `leave_requests`")
      _db.execSQL("DELETE FROM `leave_balances`")
      _db.execSQL("DELETE FROM `sync_queue`")
      super.setTransactionSuccessful()
    } finally {
      super.endTransaction()
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE")
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close()
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM")
      }
    }
  }

  protected override fun getRequiredTypeConverters(): Map<Class<out Any>, List<Class<out Any>>> {
    val _typeConvertersMap: HashMap<Class<out Any>, List<Class<out Any>>> =
        HashMap<Class<out Any>, List<Class<out Any>>>()
    _typeConvertersMap.put(UserDao::class.java, UserDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(LeaveRequestDao::class.java,
        LeaveRequestDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(LeaveBalanceDao::class.java,
        LeaveBalanceDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SyncQueueDao::class.java, SyncQueueDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecs(): Set<Class<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: HashSet<Class<out AutoMigrationSpec>> =
        HashSet<Class<out AutoMigrationSpec>>()
    return _autoMigrationSpecsSet
  }

  public override
      fun getAutoMigrations(autoMigrationSpecs: Map<Class<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = ArrayList<Migration>()
    return _autoMigrations
  }

  public override fun userDao(): UserDao = _userDao.value

  public override fun leaveRequestDao(): LeaveRequestDao = _leaveRequestDao.value

  public override fun leaveBalanceDao(): LeaveBalanceDao = _leaveBalanceDao.value

  public override fun syncQueueDao(): SyncQueueDao = _syncQueueDao.value
}
