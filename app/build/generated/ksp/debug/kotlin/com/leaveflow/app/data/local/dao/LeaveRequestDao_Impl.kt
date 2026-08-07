package com.leaveflow.app.`data`.local.dao

import android.database.Cursor
import android.os.CancellationSignal
import androidx.room.CoroutinesRoom
import androidx.room.CoroutinesRoom.Companion.execute
import androidx.room.EntityDeletionOrUpdateAdapter
import androidx.room.EntityInsertionAdapter
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.RoomSQLiteQuery.Companion.acquire
import androidx.room.SharedSQLiteStatement
import androidx.room.util.createCancellationSignal
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.query
import androidx.sqlite.db.SupportSQLiteStatement
import com.leaveflow.app.`data`.local.entity.LeaveRequestEntity
import java.lang.Class
import java.util.ArrayList
import java.util.concurrent.Callable
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.jvm.JvmStatic
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION"])
public class LeaveRequestDao_Impl(
  __db: RoomDatabase,
) : LeaveRequestDao {
  private val __db: RoomDatabase

  private val __insertionAdapterOfLeaveRequestEntity: EntityInsertionAdapter<LeaveRequestEntity>

  private val __insertionAdapterOfLeaveRequestEntity_1: EntityInsertionAdapter<LeaveRequestEntity>

  private val __deletionAdapterOfLeaveRequestEntity:
      EntityDeletionOrUpdateAdapter<LeaveRequestEntity>

  private val __updateAdapterOfLeaveRequestEntity: EntityDeletionOrUpdateAdapter<LeaveRequestEntity>

  private val __preparedStmtOfUpdateLeaveStatus: SharedSQLiteStatement

  private val __preparedStmtOfUpdateSyncStatus: SharedSQLiteStatement

  private val __preparedStmtOfDeleteRejectedRequest: SharedSQLiteStatement
  init {
    this.__db = __db
    this.__insertionAdapterOfLeaveRequestEntity = object :
        EntityInsertionAdapter<LeaveRequestEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `leave_requests` (`id`,`employeeId`,`employeeName`,`department`,`leaveType`,`startDate`,`endDate`,`reason`,`contactNumber`,`numberOfDays`,`status`,`photoPath`,`latitude`,`longitude`,`syncStatus`,`managerId`,`managerComment`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: LeaveRequestEntity) {
        statement.bindString(1, entity.id)
        statement.bindString(2, entity.employeeId)
        statement.bindString(3, entity.employeeName)
        statement.bindString(4, entity.department)
        statement.bindString(5, entity.leaveType)
        statement.bindString(6, entity.startDate)
        statement.bindString(7, entity.endDate)
        statement.bindString(8, entity.reason)
        statement.bindString(9, entity.contactNumber)
        statement.bindLong(10, entity.numberOfDays.toLong())
        statement.bindString(11, entity.status)
        val _tmpPhotoPath: String? = entity.photoPath
        if (_tmpPhotoPath == null) {
          statement.bindNull(12)
        } else {
          statement.bindString(12, _tmpPhotoPath)
        }
        val _tmpLatitude: Double? = entity.latitude
        if (_tmpLatitude == null) {
          statement.bindNull(13)
        } else {
          statement.bindDouble(13, _tmpLatitude)
        }
        val _tmpLongitude: Double? = entity.longitude
        if (_tmpLongitude == null) {
          statement.bindNull(14)
        } else {
          statement.bindDouble(14, _tmpLongitude)
        }
        statement.bindString(15, entity.syncStatus)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(16)
        } else {
          statement.bindString(16, _tmpManagerId)
        }
        val _tmpManagerComment: String? = entity.managerComment
        if (_tmpManagerComment == null) {
          statement.bindNull(17)
        } else {
          statement.bindString(17, _tmpManagerComment)
        }
        statement.bindLong(18, entity.createdAt)
        statement.bindLong(19, entity.updatedAt)
      }
    }
    this.__insertionAdapterOfLeaveRequestEntity_1 = object :
        EntityInsertionAdapter<LeaveRequestEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `leave_requests` (`id`,`employeeId`,`employeeName`,`department`,`leaveType`,`startDate`,`endDate`,`reason`,`contactNumber`,`numberOfDays`,`status`,`photoPath`,`latitude`,`longitude`,`syncStatus`,`managerId`,`managerComment`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: LeaveRequestEntity) {
        statement.bindString(1, entity.id)
        statement.bindString(2, entity.employeeId)
        statement.bindString(3, entity.employeeName)
        statement.bindString(4, entity.department)
        statement.bindString(5, entity.leaveType)
        statement.bindString(6, entity.startDate)
        statement.bindString(7, entity.endDate)
        statement.bindString(8, entity.reason)
        statement.bindString(9, entity.contactNumber)
        statement.bindLong(10, entity.numberOfDays.toLong())
        statement.bindString(11, entity.status)
        val _tmpPhotoPath: String? = entity.photoPath
        if (_tmpPhotoPath == null) {
          statement.bindNull(12)
        } else {
          statement.bindString(12, _tmpPhotoPath)
        }
        val _tmpLatitude: Double? = entity.latitude
        if (_tmpLatitude == null) {
          statement.bindNull(13)
        } else {
          statement.bindDouble(13, _tmpLatitude)
        }
        val _tmpLongitude: Double? = entity.longitude
        if (_tmpLongitude == null) {
          statement.bindNull(14)
        } else {
          statement.bindDouble(14, _tmpLongitude)
        }
        statement.bindString(15, entity.syncStatus)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(16)
        } else {
          statement.bindString(16, _tmpManagerId)
        }
        val _tmpManagerComment: String? = entity.managerComment
        if (_tmpManagerComment == null) {
          statement.bindNull(17)
        } else {
          statement.bindString(17, _tmpManagerComment)
        }
        statement.bindLong(18, entity.createdAt)
        statement.bindLong(19, entity.updatedAt)
      }
    }
    this.__deletionAdapterOfLeaveRequestEntity = object :
        EntityDeletionOrUpdateAdapter<LeaveRequestEntity>(__db) {
      protected override fun createQuery(): String = "DELETE FROM `leave_requests` WHERE `id` = ?"

      protected override fun bind(statement: SupportSQLiteStatement, entity: LeaveRequestEntity) {
        statement.bindString(1, entity.id)
      }
    }
    this.__updateAdapterOfLeaveRequestEntity = object :
        EntityDeletionOrUpdateAdapter<LeaveRequestEntity>(__db) {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `leave_requests` SET `id` = ?,`employeeId` = ?,`employeeName` = ?,`department` = ?,`leaveType` = ?,`startDate` = ?,`endDate` = ?,`reason` = ?,`contactNumber` = ?,`numberOfDays` = ?,`status` = ?,`photoPath` = ?,`latitude` = ?,`longitude` = ?,`syncStatus` = ?,`managerId` = ?,`managerComment` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SupportSQLiteStatement, entity: LeaveRequestEntity) {
        statement.bindString(1, entity.id)
        statement.bindString(2, entity.employeeId)
        statement.bindString(3, entity.employeeName)
        statement.bindString(4, entity.department)
        statement.bindString(5, entity.leaveType)
        statement.bindString(6, entity.startDate)
        statement.bindString(7, entity.endDate)
        statement.bindString(8, entity.reason)
        statement.bindString(9, entity.contactNumber)
        statement.bindLong(10, entity.numberOfDays.toLong())
        statement.bindString(11, entity.status)
        val _tmpPhotoPath: String? = entity.photoPath
        if (_tmpPhotoPath == null) {
          statement.bindNull(12)
        } else {
          statement.bindString(12, _tmpPhotoPath)
        }
        val _tmpLatitude: Double? = entity.latitude
        if (_tmpLatitude == null) {
          statement.bindNull(13)
        } else {
          statement.bindDouble(13, _tmpLatitude)
        }
        val _tmpLongitude: Double? = entity.longitude
        if (_tmpLongitude == null) {
          statement.bindNull(14)
        } else {
          statement.bindDouble(14, _tmpLongitude)
        }
        statement.bindString(15, entity.syncStatus)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(16)
        } else {
          statement.bindString(16, _tmpManagerId)
        }
        val _tmpManagerComment: String? = entity.managerComment
        if (_tmpManagerComment == null) {
          statement.bindNull(17)
        } else {
          statement.bindString(17, _tmpManagerComment)
        }
        statement.bindLong(18, entity.createdAt)
        statement.bindLong(19, entity.updatedAt)
        statement.bindString(20, entity.id)
      }
    }
    this.__preparedStmtOfUpdateLeaveStatus = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = """
            |
            |        UPDATE leave_requests
            |        SET status = ?,
            |            managerId = ?,
            |            managerComment = ?,
            |            syncStatus = 'PENDING_SYNC',
            |            updatedAt = ?
            |        WHERE id = ?
            |    
            """.trimMargin()
        return _query
      }
    }
    this.__preparedStmtOfUpdateSyncStatus = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = "UPDATE leave_requests SET syncStatus = ? WHERE id = ?"
        return _query
      }
    }
    this.__preparedStmtOfDeleteRejectedRequest = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = "DELETE FROM leave_requests WHERE id = ? AND status = 'REJECTED'"
        return _query
      }
    }
  }

  public override suspend fun insertLeaveRequest(request: LeaveRequestEntity): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __insertionAdapterOfLeaveRequestEntity.insert(request)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun insertLeaveRequests(requests: List<LeaveRequestEntity>): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __insertionAdapterOfLeaveRequestEntity_1.insert(requests)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun deleteLeaveRequest(request: LeaveRequestEntity): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __deletionAdapterOfLeaveRequestEntity.handle(request)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun updateLeaveRequest(request: LeaveRequestEntity): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __updateAdapterOfLeaveRequestEntity.handle(request)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun updateLeaveStatus(
    id: String,
    status: String,
    managerId: String,
    comment: String?,
    updatedAt: Long,
  ): Unit = CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfUpdateLeaveStatus.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, status)
      _argIndex = 2
      _stmt.bindString(_argIndex, managerId)
      _argIndex = 3
      if (comment == null) {
        _stmt.bindNull(_argIndex)
      } else {
        _stmt.bindString(_argIndex, comment)
      }
      _argIndex = 4
      _stmt.bindLong(_argIndex, updatedAt)
      _argIndex = 5
      _stmt.bindString(_argIndex, id)
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfUpdateLeaveStatus.release(_stmt)
      }
    }
  })

  public override suspend fun updateSyncStatus(id: String, syncStatus: String): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfUpdateSyncStatus.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, syncStatus)
      _argIndex = 2
      _stmt.bindString(_argIndex, id)
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfUpdateSyncStatus.release(_stmt)
      }
    }
  })

  public override suspend fun deleteRejectedRequest(id: String): Unit = CoroutinesRoom.execute(__db,
      true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfDeleteRejectedRequest.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, id)
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfDeleteRejectedRequest.release(_stmt)
      }
    }
  })

  public override suspend fun getLeaveRequestById(id: String): LeaveRequestEntity? {
    val _sql: String = "SELECT * FROM leave_requests WHERE id = ? LIMIT 1"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, id)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<LeaveRequestEntity?> {
      public override fun call(): LeaveRequestEntity? {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfEmployeeName: Int = getColumnIndexOrThrow(_cursor, "employeeName")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfLeaveType: Int = getColumnIndexOrThrow(_cursor, "leaveType")
          val _cursorIndexOfStartDate: Int = getColumnIndexOrThrow(_cursor, "startDate")
          val _cursorIndexOfEndDate: Int = getColumnIndexOrThrow(_cursor, "endDate")
          val _cursorIndexOfReason: Int = getColumnIndexOrThrow(_cursor, "reason")
          val _cursorIndexOfContactNumber: Int = getColumnIndexOrThrow(_cursor, "contactNumber")
          val _cursorIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_cursor, "numberOfDays")
          val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_cursor, "status")
          val _cursorIndexOfPhotoPath: Int = getColumnIndexOrThrow(_cursor, "photoPath")
          val _cursorIndexOfLatitude: Int = getColumnIndexOrThrow(_cursor, "latitude")
          val _cursorIndexOfLongitude: Int = getColumnIndexOrThrow(_cursor, "longitude")
          val _cursorIndexOfSyncStatus: Int = getColumnIndexOrThrow(_cursor, "syncStatus")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfManagerComment: Int = getColumnIndexOrThrow(_cursor, "managerComment")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _cursorIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_cursor, "updatedAt")
          val _result: LeaveRequestEntity?
          if (_cursor.moveToFirst()) {
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpEmployeeName: String
            _tmpEmployeeName = _cursor.getString(_cursorIndexOfEmployeeName)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpLeaveType: String
            _tmpLeaveType = _cursor.getString(_cursorIndexOfLeaveType)
            val _tmpStartDate: String
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate)
            val _tmpEndDate: String
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate)
            val _tmpReason: String
            _tmpReason = _cursor.getString(_cursorIndexOfReason)
            val _tmpContactNumber: String
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber)
            val _tmpNumberOfDays: Int
            _tmpNumberOfDays = _cursor.getInt(_cursorIndexOfNumberOfDays)
            val _tmpStatus: String
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus)
            val _tmpPhotoPath: String?
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath)
            }
            val _tmpLatitude: Double?
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude)
            }
            val _tmpLongitude: Double?
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude)
            }
            val _tmpSyncStatus: String
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpManagerComment: String?
            if (_cursor.isNull(_cursorIndexOfManagerComment)) {
              _tmpManagerComment = null
            } else {
              _tmpManagerComment = _cursor.getString(_cursorIndexOfManagerComment)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            val _tmpUpdatedAt: Long
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt)
            _result =
                LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
          } else {
            _result = null
          }
          return _result
        } finally {
          _cursor.close()
          _statement.release()
        }
      }
    })
  }

  public override fun getLeaveRequestsByEmployee(employeeId: String):
      Flow<List<LeaveRequestEntity>> {
    val _sql: String = "SELECT * FROM leave_requests WHERE employeeId = ? ORDER BY createdAt DESC"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, employeeId)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object :
        Callable<List<LeaveRequestEntity>> {
      public override fun call(): List<LeaveRequestEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfEmployeeName: Int = getColumnIndexOrThrow(_cursor, "employeeName")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfLeaveType: Int = getColumnIndexOrThrow(_cursor, "leaveType")
          val _cursorIndexOfStartDate: Int = getColumnIndexOrThrow(_cursor, "startDate")
          val _cursorIndexOfEndDate: Int = getColumnIndexOrThrow(_cursor, "endDate")
          val _cursorIndexOfReason: Int = getColumnIndexOrThrow(_cursor, "reason")
          val _cursorIndexOfContactNumber: Int = getColumnIndexOrThrow(_cursor, "contactNumber")
          val _cursorIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_cursor, "numberOfDays")
          val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_cursor, "status")
          val _cursorIndexOfPhotoPath: Int = getColumnIndexOrThrow(_cursor, "photoPath")
          val _cursorIndexOfLatitude: Int = getColumnIndexOrThrow(_cursor, "latitude")
          val _cursorIndexOfLongitude: Int = getColumnIndexOrThrow(_cursor, "longitude")
          val _cursorIndexOfSyncStatus: Int = getColumnIndexOrThrow(_cursor, "syncStatus")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfManagerComment: Int = getColumnIndexOrThrow(_cursor, "managerComment")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _cursorIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_cursor, "updatedAt")
          val _result: MutableList<LeaveRequestEntity> =
              ArrayList<LeaveRequestEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: LeaveRequestEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpEmployeeName: String
            _tmpEmployeeName = _cursor.getString(_cursorIndexOfEmployeeName)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpLeaveType: String
            _tmpLeaveType = _cursor.getString(_cursorIndexOfLeaveType)
            val _tmpStartDate: String
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate)
            val _tmpEndDate: String
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate)
            val _tmpReason: String
            _tmpReason = _cursor.getString(_cursorIndexOfReason)
            val _tmpContactNumber: String
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber)
            val _tmpNumberOfDays: Int
            _tmpNumberOfDays = _cursor.getInt(_cursorIndexOfNumberOfDays)
            val _tmpStatus: String
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus)
            val _tmpPhotoPath: String?
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath)
            }
            val _tmpLatitude: Double?
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude)
            }
            val _tmpLongitude: Double?
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude)
            }
            val _tmpSyncStatus: String
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpManagerComment: String?
            if (_cursor.isNull(_cursorIndexOfManagerComment)) {
              _tmpManagerComment = null
            } else {
              _tmpManagerComment = _cursor.getString(_cursorIndexOfManagerComment)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            val _tmpUpdatedAt: Long
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt)
            _item =
                LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun getPendingLeaveRequests(): Flow<List<LeaveRequestEntity>> {
    val _sql: String =
        "SELECT * FROM leave_requests WHERE status = 'PENDING' ORDER BY createdAt ASC"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object :
        Callable<List<LeaveRequestEntity>> {
      public override fun call(): List<LeaveRequestEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfEmployeeName: Int = getColumnIndexOrThrow(_cursor, "employeeName")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfLeaveType: Int = getColumnIndexOrThrow(_cursor, "leaveType")
          val _cursorIndexOfStartDate: Int = getColumnIndexOrThrow(_cursor, "startDate")
          val _cursorIndexOfEndDate: Int = getColumnIndexOrThrow(_cursor, "endDate")
          val _cursorIndexOfReason: Int = getColumnIndexOrThrow(_cursor, "reason")
          val _cursorIndexOfContactNumber: Int = getColumnIndexOrThrow(_cursor, "contactNumber")
          val _cursorIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_cursor, "numberOfDays")
          val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_cursor, "status")
          val _cursorIndexOfPhotoPath: Int = getColumnIndexOrThrow(_cursor, "photoPath")
          val _cursorIndexOfLatitude: Int = getColumnIndexOrThrow(_cursor, "latitude")
          val _cursorIndexOfLongitude: Int = getColumnIndexOrThrow(_cursor, "longitude")
          val _cursorIndexOfSyncStatus: Int = getColumnIndexOrThrow(_cursor, "syncStatus")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfManagerComment: Int = getColumnIndexOrThrow(_cursor, "managerComment")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _cursorIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_cursor, "updatedAt")
          val _result: MutableList<LeaveRequestEntity> =
              ArrayList<LeaveRequestEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: LeaveRequestEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpEmployeeName: String
            _tmpEmployeeName = _cursor.getString(_cursorIndexOfEmployeeName)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpLeaveType: String
            _tmpLeaveType = _cursor.getString(_cursorIndexOfLeaveType)
            val _tmpStartDate: String
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate)
            val _tmpEndDate: String
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate)
            val _tmpReason: String
            _tmpReason = _cursor.getString(_cursorIndexOfReason)
            val _tmpContactNumber: String
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber)
            val _tmpNumberOfDays: Int
            _tmpNumberOfDays = _cursor.getInt(_cursorIndexOfNumberOfDays)
            val _tmpStatus: String
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus)
            val _tmpPhotoPath: String?
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath)
            }
            val _tmpLatitude: Double?
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude)
            }
            val _tmpLongitude: Double?
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude)
            }
            val _tmpSyncStatus: String
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpManagerComment: String?
            if (_cursor.isNull(_cursorIndexOfManagerComment)) {
              _tmpManagerComment = null
            } else {
              _tmpManagerComment = _cursor.getString(_cursorIndexOfManagerComment)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            val _tmpUpdatedAt: Long
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt)
            _item =
                LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun getAllLeaveRequests(): Flow<List<LeaveRequestEntity>> {
    val _sql: String = "SELECT * FROM leave_requests ORDER BY createdAt DESC"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object :
        Callable<List<LeaveRequestEntity>> {
      public override fun call(): List<LeaveRequestEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfEmployeeName: Int = getColumnIndexOrThrow(_cursor, "employeeName")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfLeaveType: Int = getColumnIndexOrThrow(_cursor, "leaveType")
          val _cursorIndexOfStartDate: Int = getColumnIndexOrThrow(_cursor, "startDate")
          val _cursorIndexOfEndDate: Int = getColumnIndexOrThrow(_cursor, "endDate")
          val _cursorIndexOfReason: Int = getColumnIndexOrThrow(_cursor, "reason")
          val _cursorIndexOfContactNumber: Int = getColumnIndexOrThrow(_cursor, "contactNumber")
          val _cursorIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_cursor, "numberOfDays")
          val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_cursor, "status")
          val _cursorIndexOfPhotoPath: Int = getColumnIndexOrThrow(_cursor, "photoPath")
          val _cursorIndexOfLatitude: Int = getColumnIndexOrThrow(_cursor, "latitude")
          val _cursorIndexOfLongitude: Int = getColumnIndexOrThrow(_cursor, "longitude")
          val _cursorIndexOfSyncStatus: Int = getColumnIndexOrThrow(_cursor, "syncStatus")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfManagerComment: Int = getColumnIndexOrThrow(_cursor, "managerComment")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _cursorIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_cursor, "updatedAt")
          val _result: MutableList<LeaveRequestEntity> =
              ArrayList<LeaveRequestEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: LeaveRequestEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpEmployeeName: String
            _tmpEmployeeName = _cursor.getString(_cursorIndexOfEmployeeName)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpLeaveType: String
            _tmpLeaveType = _cursor.getString(_cursorIndexOfLeaveType)
            val _tmpStartDate: String
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate)
            val _tmpEndDate: String
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate)
            val _tmpReason: String
            _tmpReason = _cursor.getString(_cursorIndexOfReason)
            val _tmpContactNumber: String
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber)
            val _tmpNumberOfDays: Int
            _tmpNumberOfDays = _cursor.getInt(_cursorIndexOfNumberOfDays)
            val _tmpStatus: String
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus)
            val _tmpPhotoPath: String?
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath)
            }
            val _tmpLatitude: Double?
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude)
            }
            val _tmpLongitude: Double?
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude)
            }
            val _tmpSyncStatus: String
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpManagerComment: String?
            if (_cursor.isNull(_cursorIndexOfManagerComment)) {
              _tmpManagerComment = null
            } else {
              _tmpManagerComment = _cursor.getString(_cursorIndexOfManagerComment)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            val _tmpUpdatedAt: Long
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt)
            _item =
                LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override suspend fun getUnsyncedRequests(): List<LeaveRequestEntity> {
    val _sql: String =
        "SELECT * FROM leave_requests WHERE syncStatus = 'PENDING_SYNC' OR syncStatus = 'FAILED'"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<List<LeaveRequestEntity>> {
      public override fun call(): List<LeaveRequestEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfEmployeeName: Int = getColumnIndexOrThrow(_cursor, "employeeName")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfLeaveType: Int = getColumnIndexOrThrow(_cursor, "leaveType")
          val _cursorIndexOfStartDate: Int = getColumnIndexOrThrow(_cursor, "startDate")
          val _cursorIndexOfEndDate: Int = getColumnIndexOrThrow(_cursor, "endDate")
          val _cursorIndexOfReason: Int = getColumnIndexOrThrow(_cursor, "reason")
          val _cursorIndexOfContactNumber: Int = getColumnIndexOrThrow(_cursor, "contactNumber")
          val _cursorIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_cursor, "numberOfDays")
          val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_cursor, "status")
          val _cursorIndexOfPhotoPath: Int = getColumnIndexOrThrow(_cursor, "photoPath")
          val _cursorIndexOfLatitude: Int = getColumnIndexOrThrow(_cursor, "latitude")
          val _cursorIndexOfLongitude: Int = getColumnIndexOrThrow(_cursor, "longitude")
          val _cursorIndexOfSyncStatus: Int = getColumnIndexOrThrow(_cursor, "syncStatus")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfManagerComment: Int = getColumnIndexOrThrow(_cursor, "managerComment")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _cursorIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_cursor, "updatedAt")
          val _result: MutableList<LeaveRequestEntity> =
              ArrayList<LeaveRequestEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: LeaveRequestEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpEmployeeName: String
            _tmpEmployeeName = _cursor.getString(_cursorIndexOfEmployeeName)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpLeaveType: String
            _tmpLeaveType = _cursor.getString(_cursorIndexOfLeaveType)
            val _tmpStartDate: String
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate)
            val _tmpEndDate: String
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate)
            val _tmpReason: String
            _tmpReason = _cursor.getString(_cursorIndexOfReason)
            val _tmpContactNumber: String
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber)
            val _tmpNumberOfDays: Int
            _tmpNumberOfDays = _cursor.getInt(_cursorIndexOfNumberOfDays)
            val _tmpStatus: String
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus)
            val _tmpPhotoPath: String?
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath)
            }
            val _tmpLatitude: Double?
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude)
            }
            val _tmpLongitude: Double?
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude)
            }
            val _tmpSyncStatus: String
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpManagerComment: String?
            if (_cursor.isNull(_cursorIndexOfManagerComment)) {
              _tmpManagerComment = null
            } else {
              _tmpManagerComment = _cursor.getString(_cursorIndexOfManagerComment)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            val _tmpUpdatedAt: Long
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt)
            _item =
                LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
          _statement.release()
        }
      }
    })
  }

  public override fun getCountByStatus(status: String): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = ?"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, status)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object : Callable<Int>
        {
      public override fun call(): Int {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _result: Int
          if (_cursor.moveToFirst()) {
            val _tmp: Int
            _tmp = _cursor.getInt(0)
            _result = _tmp
          } else {
            _result = 0
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun getApprovedCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = 'APPROVED'"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object : Callable<Int>
        {
      public override fun call(): Int {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _result: Int
          if (_cursor.moveToFirst()) {
            val _tmp: Int
            _tmp = _cursor.getInt(0)
            _result = _tmp
          } else {
            _result = 0
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun getPendingCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = 'PENDING'"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object : Callable<Int>
        {
      public override fun call(): Int {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _result: Int
          if (_cursor.moveToFirst()) {
            val _tmp: Int
            _tmp = _cursor.getInt(0)
            _result = _tmp
          } else {
            _result = 0
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun getRejectedCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = 'REJECTED'"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object : Callable<Int>
        {
      public override fun call(): Int {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _result: Int
          if (_cursor.moveToFirst()) {
            val _tmp: Int
            _tmp = _cursor.getInt(0)
            _result = _tmp
          } else {
            _result = 0
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun getTotalCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_requests"), object : Callable<Int>
        {
      public override fun call(): Int {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _result: Int
          if (_cursor.moveToFirst()) {
            val _tmp: Int
            _tmp = _cursor.getInt(0)
            _result = _tmp
          } else {
            _result = 0
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public companion object {
    @JvmStatic
    public fun getRequiredConverters(): List<Class<*>> = emptyList()
  }
}
