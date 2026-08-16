package com.leaveflow.app.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.leaveflow.app.`data`.local.entity.LeaveRequestEntity
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class LeaveRequestDao_Impl(
  __db: RoomDatabase,
) : LeaveRequestDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfLeaveRequestEntity: EntityInsertAdapter<LeaveRequestEntity>

  private val __insertAdapterOfLeaveRequestEntity_1: EntityInsertAdapter<LeaveRequestEntity>

  private val __updateAdapterOfLeaveRequestEntity: EntityDeleteOrUpdateAdapter<LeaveRequestEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfLeaveRequestEntity = object : EntityInsertAdapter<LeaveRequestEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `leave_requests` (`id`,`employeeId`,`employeeName`,`department`,`leaveType`,`startDate`,`endDate`,`reason`,`contactNumber`,`numberOfDays`,`status`,`photoPath`,`latitude`,`longitude`,`syncStatus`,`managerId`,`managerComment`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LeaveRequestEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.employeeId)
        statement.bindText(3, entity.employeeName)
        statement.bindText(4, entity.department)
        statement.bindText(5, entity.leaveType)
        statement.bindText(6, entity.startDate)
        statement.bindText(7, entity.endDate)
        statement.bindText(8, entity.reason)
        statement.bindText(9, entity.contactNumber)
        statement.bindLong(10, entity.numberOfDays.toLong())
        statement.bindText(11, entity.status)
        val _tmpPhotoPath: String? = entity.photoPath
        if (_tmpPhotoPath == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPhotoPath)
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
        statement.bindText(15, entity.syncStatus)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(16)
        } else {
          statement.bindText(16, _tmpManagerId)
        }
        val _tmpManagerComment: String? = entity.managerComment
        if (_tmpManagerComment == null) {
          statement.bindNull(17)
        } else {
          statement.bindText(17, _tmpManagerComment)
        }
        statement.bindLong(18, entity.createdAt)
        statement.bindLong(19, entity.updatedAt)
      }
    }
    this.__insertAdapterOfLeaveRequestEntity_1 = object : EntityInsertAdapter<LeaveRequestEntity>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `leave_requests` (`id`,`employeeId`,`employeeName`,`department`,`leaveType`,`startDate`,`endDate`,`reason`,`contactNumber`,`numberOfDays`,`status`,`photoPath`,`latitude`,`longitude`,`syncStatus`,`managerId`,`managerComment`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LeaveRequestEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.employeeId)
        statement.bindText(3, entity.employeeName)
        statement.bindText(4, entity.department)
        statement.bindText(5, entity.leaveType)
        statement.bindText(6, entity.startDate)
        statement.bindText(7, entity.endDate)
        statement.bindText(8, entity.reason)
        statement.bindText(9, entity.contactNumber)
        statement.bindLong(10, entity.numberOfDays.toLong())
        statement.bindText(11, entity.status)
        val _tmpPhotoPath: String? = entity.photoPath
        if (_tmpPhotoPath == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPhotoPath)
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
        statement.bindText(15, entity.syncStatus)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(16)
        } else {
          statement.bindText(16, _tmpManagerId)
        }
        val _tmpManagerComment: String? = entity.managerComment
        if (_tmpManagerComment == null) {
          statement.bindNull(17)
        } else {
          statement.bindText(17, _tmpManagerComment)
        }
        statement.bindLong(18, entity.createdAt)
        statement.bindLong(19, entity.updatedAt)
      }
    }
    this.__updateAdapterOfLeaveRequestEntity = object : EntityDeleteOrUpdateAdapter<LeaveRequestEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `leave_requests` SET `id` = ?,`employeeId` = ?,`employeeName` = ?,`department` = ?,`leaveType` = ?,`startDate` = ?,`endDate` = ?,`reason` = ?,`contactNumber` = ?,`numberOfDays` = ?,`status` = ?,`photoPath` = ?,`latitude` = ?,`longitude` = ?,`syncStatus` = ?,`managerId` = ?,`managerComment` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: LeaveRequestEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.employeeId)
        statement.bindText(3, entity.employeeName)
        statement.bindText(4, entity.department)
        statement.bindText(5, entity.leaveType)
        statement.bindText(6, entity.startDate)
        statement.bindText(7, entity.endDate)
        statement.bindText(8, entity.reason)
        statement.bindText(9, entity.contactNumber)
        statement.bindLong(10, entity.numberOfDays.toLong())
        statement.bindText(11, entity.status)
        val _tmpPhotoPath: String? = entity.photoPath
        if (_tmpPhotoPath == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPhotoPath)
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
        statement.bindText(15, entity.syncStatus)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(16)
        } else {
          statement.bindText(16, _tmpManagerId)
        }
        val _tmpManagerComment: String? = entity.managerComment
        if (_tmpManagerComment == null) {
          statement.bindNull(17)
        } else {
          statement.bindText(17, _tmpManagerComment)
        }
        statement.bindLong(18, entity.createdAt)
        statement.bindLong(19, entity.updatedAt)
        statement.bindText(20, entity.id)
      }
    }
  }

  public override suspend fun insertLeaveRequest(request: LeaveRequestEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfLeaveRequestEntity.insert(_connection, request)
  }

  public override suspend fun insertLeaveRequests(requests: List<LeaveRequestEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfLeaveRequestEntity_1.insert(_connection, requests)
  }

  public override suspend fun updateLeaveRequest(request: LeaveRequestEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfLeaveRequestEntity.handle(_connection, request)
  }

  public override suspend fun getLeaveRequestById(id: String): LeaveRequestEntity? {
    val _sql: String = "SELECT * FROM leave_requests WHERE id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfEmployeeName: Int = getColumnIndexOrThrow(_stmt, "employeeName")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfLeaveType: Int = getColumnIndexOrThrow(_stmt, "leaveType")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfContactNumber: Int = getColumnIndexOrThrow(_stmt, "contactNumber")
        val _columnIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_stmt, "numberOfDays")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPhotoPath: Int = getColumnIndexOrThrow(_stmt, "photoPath")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfManagerComment: Int = getColumnIndexOrThrow(_stmt, "managerComment")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: LeaveRequestEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpEmployeeName: String
          _tmpEmployeeName = _stmt.getText(_columnIndexOfEmployeeName)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpLeaveType: String
          _tmpLeaveType = _stmt.getText(_columnIndexOfLeaveType)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpContactNumber: String
          _tmpContactNumber = _stmt.getText(_columnIndexOfContactNumber)
          val _tmpNumberOfDays: Int
          _tmpNumberOfDays = _stmt.getLong(_columnIndexOfNumberOfDays).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPhotoPath: String?
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath)
          }
          val _tmpLatitude: Double?
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          }
          val _tmpLongitude: Double?
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          }
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpManagerComment: String?
          if (_stmt.isNull(_columnIndexOfManagerComment)) {
            _tmpManagerComment = null
          } else {
            _tmpManagerComment = _stmt.getText(_columnIndexOfManagerComment)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLeaveRequestsByEmployee(employeeId: String): Flow<List<LeaveRequestEntity>> {
    val _sql: String = "SELECT * FROM leave_requests WHERE employeeId = ? ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, employeeId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfEmployeeName: Int = getColumnIndexOrThrow(_stmt, "employeeName")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfLeaveType: Int = getColumnIndexOrThrow(_stmt, "leaveType")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfContactNumber: Int = getColumnIndexOrThrow(_stmt, "contactNumber")
        val _columnIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_stmt, "numberOfDays")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPhotoPath: Int = getColumnIndexOrThrow(_stmt, "photoPath")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfManagerComment: Int = getColumnIndexOrThrow(_stmt, "managerComment")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<LeaveRequestEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LeaveRequestEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpEmployeeName: String
          _tmpEmployeeName = _stmt.getText(_columnIndexOfEmployeeName)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpLeaveType: String
          _tmpLeaveType = _stmt.getText(_columnIndexOfLeaveType)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpContactNumber: String
          _tmpContactNumber = _stmt.getText(_columnIndexOfContactNumber)
          val _tmpNumberOfDays: Int
          _tmpNumberOfDays = _stmt.getLong(_columnIndexOfNumberOfDays).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPhotoPath: String?
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath)
          }
          val _tmpLatitude: Double?
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          }
          val _tmpLongitude: Double?
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          }
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpManagerComment: String?
          if (_stmt.isNull(_columnIndexOfManagerComment)) {
            _tmpManagerComment = null
          } else {
            _tmpManagerComment = _stmt.getText(_columnIndexOfManagerComment)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getPendingLeaveRequests(): Flow<List<LeaveRequestEntity>> {
    val _sql: String = "SELECT * FROM leave_requests WHERE status = 'PENDING' ORDER BY createdAt ASC"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfEmployeeName: Int = getColumnIndexOrThrow(_stmt, "employeeName")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfLeaveType: Int = getColumnIndexOrThrow(_stmt, "leaveType")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfContactNumber: Int = getColumnIndexOrThrow(_stmt, "contactNumber")
        val _columnIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_stmt, "numberOfDays")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPhotoPath: Int = getColumnIndexOrThrow(_stmt, "photoPath")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfManagerComment: Int = getColumnIndexOrThrow(_stmt, "managerComment")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<LeaveRequestEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LeaveRequestEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpEmployeeName: String
          _tmpEmployeeName = _stmt.getText(_columnIndexOfEmployeeName)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpLeaveType: String
          _tmpLeaveType = _stmt.getText(_columnIndexOfLeaveType)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpContactNumber: String
          _tmpContactNumber = _stmt.getText(_columnIndexOfContactNumber)
          val _tmpNumberOfDays: Int
          _tmpNumberOfDays = _stmt.getLong(_columnIndexOfNumberOfDays).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPhotoPath: String?
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath)
          }
          val _tmpLatitude: Double?
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          }
          val _tmpLongitude: Double?
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          }
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpManagerComment: String?
          if (_stmt.isNull(_columnIndexOfManagerComment)) {
            _tmpManagerComment = null
          } else {
            _tmpManagerComment = _stmt.getText(_columnIndexOfManagerComment)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllLeaveRequests(): Flow<List<LeaveRequestEntity>> {
    val _sql: String = "SELECT * FROM leave_requests ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfEmployeeName: Int = getColumnIndexOrThrow(_stmt, "employeeName")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfLeaveType: Int = getColumnIndexOrThrow(_stmt, "leaveType")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfContactNumber: Int = getColumnIndexOrThrow(_stmt, "contactNumber")
        val _columnIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_stmt, "numberOfDays")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPhotoPath: Int = getColumnIndexOrThrow(_stmt, "photoPath")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfManagerComment: Int = getColumnIndexOrThrow(_stmt, "managerComment")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<LeaveRequestEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LeaveRequestEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpEmployeeName: String
          _tmpEmployeeName = _stmt.getText(_columnIndexOfEmployeeName)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpLeaveType: String
          _tmpLeaveType = _stmt.getText(_columnIndexOfLeaveType)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpContactNumber: String
          _tmpContactNumber = _stmt.getText(_columnIndexOfContactNumber)
          val _tmpNumberOfDays: Int
          _tmpNumberOfDays = _stmt.getLong(_columnIndexOfNumberOfDays).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPhotoPath: String?
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath)
          }
          val _tmpLatitude: Double?
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          }
          val _tmpLongitude: Double?
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          }
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpManagerComment: String?
          if (_stmt.isNull(_columnIndexOfManagerComment)) {
            _tmpManagerComment = null
          } else {
            _tmpManagerComment = _stmt.getText(_columnIndexOfManagerComment)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUnsyncedRequests(): List<LeaveRequestEntity> {
    val _sql: String = "SELECT * FROM leave_requests WHERE syncStatus = 'PENDING_SYNC' OR syncStatus = 'FAILED'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfEmployeeName: Int = getColumnIndexOrThrow(_stmt, "employeeName")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfLeaveType: Int = getColumnIndexOrThrow(_stmt, "leaveType")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfContactNumber: Int = getColumnIndexOrThrow(_stmt, "contactNumber")
        val _columnIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_stmt, "numberOfDays")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPhotoPath: Int = getColumnIndexOrThrow(_stmt, "photoPath")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfManagerComment: Int = getColumnIndexOrThrow(_stmt, "managerComment")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<LeaveRequestEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LeaveRequestEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpEmployeeName: String
          _tmpEmployeeName = _stmt.getText(_columnIndexOfEmployeeName)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpLeaveType: String
          _tmpLeaveType = _stmt.getText(_columnIndexOfLeaveType)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpContactNumber: String
          _tmpContactNumber = _stmt.getText(_columnIndexOfContactNumber)
          val _tmpNumberOfDays: Int
          _tmpNumberOfDays = _stmt.getLong(_columnIndexOfNumberOfDays).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPhotoPath: String?
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath)
          }
          val _tmpLatitude: Double?
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          }
          val _tmpLongitude: Double?
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          }
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpManagerComment: String?
          if (_stmt.isNull(_columnIndexOfManagerComment)) {
            _tmpManagerComment = null
          } else {
            _tmpManagerComment = _stmt.getText(_columnIndexOfManagerComment)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getCountByStatus(status: String): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = ?"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getApprovedCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = 'APPROVED'"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getPendingCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = 'PENDING'"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getRejectedCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests WHERE status = 'REJECTED'"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTotalCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM leave_requests"
    return createFlow(__db, false, arrayOf("leave_requests")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getOverlappingActiveLeave(
    employeeId: String,
    startDate: String,
    endDate: String,
  ): LeaveRequestEntity? {
    val _sql: String = """
        |
        |        SELECT * FROM leave_requests
        |        WHERE employeeId = ?
        |          AND status IN ('PENDING', 'APPROVED')
        |          AND startDate <= ?
        |          AND endDate   >= ?
        |        LIMIT 1
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, employeeId)
        _argIndex = 2
        _stmt.bindText(_argIndex, endDate)
        _argIndex = 3
        _stmt.bindText(_argIndex, startDate)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfEmployeeName: Int = getColumnIndexOrThrow(_stmt, "employeeName")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfLeaveType: Int = getColumnIndexOrThrow(_stmt, "leaveType")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfEndDate: Int = getColumnIndexOrThrow(_stmt, "endDate")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfContactNumber: Int = getColumnIndexOrThrow(_stmt, "contactNumber")
        val _columnIndexOfNumberOfDays: Int = getColumnIndexOrThrow(_stmt, "numberOfDays")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfPhotoPath: Int = getColumnIndexOrThrow(_stmt, "photoPath")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfManagerComment: Int = getColumnIndexOrThrow(_stmt, "managerComment")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: LeaveRequestEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpEmployeeName: String
          _tmpEmployeeName = _stmt.getText(_columnIndexOfEmployeeName)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpLeaveType: String
          _tmpLeaveType = _stmt.getText(_columnIndexOfLeaveType)
          val _tmpStartDate: String
          _tmpStartDate = _stmt.getText(_columnIndexOfStartDate)
          val _tmpEndDate: String
          _tmpEndDate = _stmt.getText(_columnIndexOfEndDate)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpContactNumber: String
          _tmpContactNumber = _stmt.getText(_columnIndexOfContactNumber)
          val _tmpNumberOfDays: Int
          _tmpNumberOfDays = _stmt.getLong(_columnIndexOfNumberOfDays).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpPhotoPath: String?
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath)
          }
          val _tmpLatitude: Double?
          if (_stmt.isNull(_columnIndexOfLatitude)) {
            _tmpLatitude = null
          } else {
            _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          }
          val _tmpLongitude: Double?
          if (_stmt.isNull(_columnIndexOfLongitude)) {
            _tmpLongitude = null
          } else {
            _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          }
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpManagerComment: String?
          if (_stmt.isNull(_columnIndexOfManagerComment)) {
            _tmpManagerComment = null
          } else {
            _tmpManagerComment = _stmt.getText(_columnIndexOfManagerComment)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = LeaveRequestEntity(_tmpId,_tmpEmployeeId,_tmpEmployeeName,_tmpDepartment,_tmpLeaveType,_tmpStartDate,_tmpEndDate,_tmpReason,_tmpContactNumber,_tmpNumberOfDays,_tmpStatus,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpSyncStatus,_tmpManagerId,_tmpManagerComment,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteLeaveRequestById(id: String) {
    val _sql: String = "DELETE FROM leave_requests WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLeaveStatus(
    id: String,
    status: String,
    managerId: String,
    comment: String?,
    updatedAt: Long,
  ) {
    val _sql: String = """
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
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        _argIndex = 2
        _stmt.bindText(_argIndex, managerId)
        _argIndex = 3
        if (comment == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, comment)
        }
        _argIndex = 4
        _stmt.bindLong(_argIndex, updatedAt)
        _argIndex = 5
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateSyncStatus(id: String, syncStatus: String) {
    val _sql: String = "UPDATE leave_requests SET syncStatus = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, syncStatus)
        _argIndex = 2
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteRejectedRequest(id: String) {
    val _sql: String = "DELETE FROM leave_requests WHERE id = ? AND status = 'REJECTED'"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
