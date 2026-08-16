package com.leaveflow.app.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.leaveflow.app.`data`.local.entity.LeaveBalanceEntity
import javax.`annotation`.processing.Generated
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
public class LeaveBalanceDao_Impl(
  __db: RoomDatabase,
) : LeaveBalanceDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfLeaveBalanceEntity: EntityInsertAdapter<LeaveBalanceEntity>

  private val __insertAdapterOfLeaveBalanceEntity_1: EntityInsertAdapter<LeaveBalanceEntity>

  private val __updateAdapterOfLeaveBalanceEntity: EntityDeleteOrUpdateAdapter<LeaveBalanceEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfLeaveBalanceEntity = object : EntityInsertAdapter<LeaveBalanceEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `leave_balances` (`employeeId`,`annualTotal`,`annualUsed`,`annualPending`,`casualTotal`,`casualUsed`,`casualPending`,`medicalTotal`,`medicalUsed`,`medicalPending`,`noPayUsed`,`noPayPending`,`lastUpdated`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LeaveBalanceEntity) {
        statement.bindText(1, entity.employeeId)
        statement.bindLong(2, entity.annualTotal.toLong())
        statement.bindLong(3, entity.annualUsed.toLong())
        statement.bindLong(4, entity.annualPending.toLong())
        statement.bindLong(5, entity.casualTotal.toLong())
        statement.bindLong(6, entity.casualUsed.toLong())
        statement.bindLong(7, entity.casualPending.toLong())
        statement.bindLong(8, entity.medicalTotal.toLong())
        statement.bindLong(9, entity.medicalUsed.toLong())
        statement.bindLong(10, entity.medicalPending.toLong())
        statement.bindLong(11, entity.noPayUsed.toLong())
        statement.bindLong(12, entity.noPayPending.toLong())
        statement.bindLong(13, entity.lastUpdated)
      }
    }
    this.__insertAdapterOfLeaveBalanceEntity_1 = object : EntityInsertAdapter<LeaveBalanceEntity>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `leave_balances` (`employeeId`,`annualTotal`,`annualUsed`,`annualPending`,`casualTotal`,`casualUsed`,`casualPending`,`medicalTotal`,`medicalUsed`,`medicalPending`,`noPayUsed`,`noPayPending`,`lastUpdated`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LeaveBalanceEntity) {
        statement.bindText(1, entity.employeeId)
        statement.bindLong(2, entity.annualTotal.toLong())
        statement.bindLong(3, entity.annualUsed.toLong())
        statement.bindLong(4, entity.annualPending.toLong())
        statement.bindLong(5, entity.casualTotal.toLong())
        statement.bindLong(6, entity.casualUsed.toLong())
        statement.bindLong(7, entity.casualPending.toLong())
        statement.bindLong(8, entity.medicalTotal.toLong())
        statement.bindLong(9, entity.medicalUsed.toLong())
        statement.bindLong(10, entity.medicalPending.toLong())
        statement.bindLong(11, entity.noPayUsed.toLong())
        statement.bindLong(12, entity.noPayPending.toLong())
        statement.bindLong(13, entity.lastUpdated)
      }
    }
    this.__updateAdapterOfLeaveBalanceEntity = object : EntityDeleteOrUpdateAdapter<LeaveBalanceEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `leave_balances` SET `employeeId` = ?,`annualTotal` = ?,`annualUsed` = ?,`annualPending` = ?,`casualTotal` = ?,`casualUsed` = ?,`casualPending` = ?,`medicalTotal` = ?,`medicalUsed` = ?,`medicalPending` = ?,`noPayUsed` = ?,`noPayPending` = ?,`lastUpdated` = ? WHERE `employeeId` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: LeaveBalanceEntity) {
        statement.bindText(1, entity.employeeId)
        statement.bindLong(2, entity.annualTotal.toLong())
        statement.bindLong(3, entity.annualUsed.toLong())
        statement.bindLong(4, entity.annualPending.toLong())
        statement.bindLong(5, entity.casualTotal.toLong())
        statement.bindLong(6, entity.casualUsed.toLong())
        statement.bindLong(7, entity.casualPending.toLong())
        statement.bindLong(8, entity.medicalTotal.toLong())
        statement.bindLong(9, entity.medicalUsed.toLong())
        statement.bindLong(10, entity.medicalPending.toLong())
        statement.bindLong(11, entity.noPayUsed.toLong())
        statement.bindLong(12, entity.noPayPending.toLong())
        statement.bindLong(13, entity.lastUpdated)
        statement.bindText(14, entity.employeeId)
      }
    }
  }

  public override suspend fun insertBalance(balance: LeaveBalanceEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfLeaveBalanceEntity.insert(_connection, balance)
  }

  public override suspend fun insertBalances(balances: List<LeaveBalanceEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfLeaveBalanceEntity_1.insert(_connection, balances)
  }

  public override suspend fun updateBalance(balance: LeaveBalanceEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfLeaveBalanceEntity.handle(_connection, balance)
  }

  public override fun getBalanceByEmployee(employeeId: String): Flow<LeaveBalanceEntity?> {
    val _sql: String = "SELECT * FROM leave_balances WHERE employeeId = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("leave_balances")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, employeeId)
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfAnnualTotal: Int = getColumnIndexOrThrow(_stmt, "annualTotal")
        val _columnIndexOfAnnualUsed: Int = getColumnIndexOrThrow(_stmt, "annualUsed")
        val _columnIndexOfAnnualPending: Int = getColumnIndexOrThrow(_stmt, "annualPending")
        val _columnIndexOfCasualTotal: Int = getColumnIndexOrThrow(_stmt, "casualTotal")
        val _columnIndexOfCasualUsed: Int = getColumnIndexOrThrow(_stmt, "casualUsed")
        val _columnIndexOfCasualPending: Int = getColumnIndexOrThrow(_stmt, "casualPending")
        val _columnIndexOfMedicalTotal: Int = getColumnIndexOrThrow(_stmt, "medicalTotal")
        val _columnIndexOfMedicalUsed: Int = getColumnIndexOrThrow(_stmt, "medicalUsed")
        val _columnIndexOfMedicalPending: Int = getColumnIndexOrThrow(_stmt, "medicalPending")
        val _columnIndexOfNoPayUsed: Int = getColumnIndexOrThrow(_stmt, "noPayUsed")
        val _columnIndexOfNoPayPending: Int = getColumnIndexOrThrow(_stmt, "noPayPending")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _result: LeaveBalanceEntity?
        if (_stmt.step()) {
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpAnnualTotal: Int
          _tmpAnnualTotal = _stmt.getLong(_columnIndexOfAnnualTotal).toInt()
          val _tmpAnnualUsed: Int
          _tmpAnnualUsed = _stmt.getLong(_columnIndexOfAnnualUsed).toInt()
          val _tmpAnnualPending: Int
          _tmpAnnualPending = _stmt.getLong(_columnIndexOfAnnualPending).toInt()
          val _tmpCasualTotal: Int
          _tmpCasualTotal = _stmt.getLong(_columnIndexOfCasualTotal).toInt()
          val _tmpCasualUsed: Int
          _tmpCasualUsed = _stmt.getLong(_columnIndexOfCasualUsed).toInt()
          val _tmpCasualPending: Int
          _tmpCasualPending = _stmt.getLong(_columnIndexOfCasualPending).toInt()
          val _tmpMedicalTotal: Int
          _tmpMedicalTotal = _stmt.getLong(_columnIndexOfMedicalTotal).toInt()
          val _tmpMedicalUsed: Int
          _tmpMedicalUsed = _stmt.getLong(_columnIndexOfMedicalUsed).toInt()
          val _tmpMedicalPending: Int
          _tmpMedicalPending = _stmt.getLong(_columnIndexOfMedicalPending).toInt()
          val _tmpNoPayUsed: Int
          _tmpNoPayUsed = _stmt.getLong(_columnIndexOfNoPayUsed).toInt()
          val _tmpNoPayPending: Int
          _tmpNoPayPending = _stmt.getLong(_columnIndexOfNoPayPending).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          _result = LeaveBalanceEntity(_tmpEmployeeId,_tmpAnnualTotal,_tmpAnnualUsed,_tmpAnnualPending,_tmpCasualTotal,_tmpCasualUsed,_tmpCasualPending,_tmpMedicalTotal,_tmpMedicalUsed,_tmpMedicalPending,_tmpNoPayUsed,_tmpNoPayPending,_tmpLastUpdated)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getBalanceByEmployeeOnce(employeeId: String): LeaveBalanceEntity? {
    val _sql: String = "SELECT * FROM leave_balances WHERE employeeId = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, employeeId)
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfAnnualTotal: Int = getColumnIndexOrThrow(_stmt, "annualTotal")
        val _columnIndexOfAnnualUsed: Int = getColumnIndexOrThrow(_stmt, "annualUsed")
        val _columnIndexOfAnnualPending: Int = getColumnIndexOrThrow(_stmt, "annualPending")
        val _columnIndexOfCasualTotal: Int = getColumnIndexOrThrow(_stmt, "casualTotal")
        val _columnIndexOfCasualUsed: Int = getColumnIndexOrThrow(_stmt, "casualUsed")
        val _columnIndexOfCasualPending: Int = getColumnIndexOrThrow(_stmt, "casualPending")
        val _columnIndexOfMedicalTotal: Int = getColumnIndexOrThrow(_stmt, "medicalTotal")
        val _columnIndexOfMedicalUsed: Int = getColumnIndexOrThrow(_stmt, "medicalUsed")
        val _columnIndexOfMedicalPending: Int = getColumnIndexOrThrow(_stmt, "medicalPending")
        val _columnIndexOfNoPayUsed: Int = getColumnIndexOrThrow(_stmt, "noPayUsed")
        val _columnIndexOfNoPayPending: Int = getColumnIndexOrThrow(_stmt, "noPayPending")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _result: LeaveBalanceEntity?
        if (_stmt.step()) {
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpAnnualTotal: Int
          _tmpAnnualTotal = _stmt.getLong(_columnIndexOfAnnualTotal).toInt()
          val _tmpAnnualUsed: Int
          _tmpAnnualUsed = _stmt.getLong(_columnIndexOfAnnualUsed).toInt()
          val _tmpAnnualPending: Int
          _tmpAnnualPending = _stmt.getLong(_columnIndexOfAnnualPending).toInt()
          val _tmpCasualTotal: Int
          _tmpCasualTotal = _stmt.getLong(_columnIndexOfCasualTotal).toInt()
          val _tmpCasualUsed: Int
          _tmpCasualUsed = _stmt.getLong(_columnIndexOfCasualUsed).toInt()
          val _tmpCasualPending: Int
          _tmpCasualPending = _stmt.getLong(_columnIndexOfCasualPending).toInt()
          val _tmpMedicalTotal: Int
          _tmpMedicalTotal = _stmt.getLong(_columnIndexOfMedicalTotal).toInt()
          val _tmpMedicalUsed: Int
          _tmpMedicalUsed = _stmt.getLong(_columnIndexOfMedicalUsed).toInt()
          val _tmpMedicalPending: Int
          _tmpMedicalPending = _stmt.getLong(_columnIndexOfMedicalPending).toInt()
          val _tmpNoPayUsed: Int
          _tmpNoPayUsed = _stmt.getLong(_columnIndexOfNoPayUsed).toInt()
          val _tmpNoPayPending: Int
          _tmpNoPayPending = _stmt.getLong(_columnIndexOfNoPayPending).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          _result = LeaveBalanceEntity(_tmpEmployeeId,_tmpAnnualTotal,_tmpAnnualUsed,_tmpAnnualPending,_tmpCasualTotal,_tmpCasualUsed,_tmpCasualPending,_tmpMedicalTotal,_tmpMedicalUsed,_tmpMedicalPending,_tmpNoPayUsed,_tmpNoPayPending,_tmpLastUpdated)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllBalances(): Flow<List<LeaveBalanceEntity>> {
    val _sql: String = "SELECT * FROM leave_balances"
    return createFlow(__db, false, arrayOf("leave_balances")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfAnnualTotal: Int = getColumnIndexOrThrow(_stmt, "annualTotal")
        val _columnIndexOfAnnualUsed: Int = getColumnIndexOrThrow(_stmt, "annualUsed")
        val _columnIndexOfAnnualPending: Int = getColumnIndexOrThrow(_stmt, "annualPending")
        val _columnIndexOfCasualTotal: Int = getColumnIndexOrThrow(_stmt, "casualTotal")
        val _columnIndexOfCasualUsed: Int = getColumnIndexOrThrow(_stmt, "casualUsed")
        val _columnIndexOfCasualPending: Int = getColumnIndexOrThrow(_stmt, "casualPending")
        val _columnIndexOfMedicalTotal: Int = getColumnIndexOrThrow(_stmt, "medicalTotal")
        val _columnIndexOfMedicalUsed: Int = getColumnIndexOrThrow(_stmt, "medicalUsed")
        val _columnIndexOfMedicalPending: Int = getColumnIndexOrThrow(_stmt, "medicalPending")
        val _columnIndexOfNoPayUsed: Int = getColumnIndexOrThrow(_stmt, "noPayUsed")
        val _columnIndexOfNoPayPending: Int = getColumnIndexOrThrow(_stmt, "noPayPending")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _result: MutableList<LeaveBalanceEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LeaveBalanceEntity
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpAnnualTotal: Int
          _tmpAnnualTotal = _stmt.getLong(_columnIndexOfAnnualTotal).toInt()
          val _tmpAnnualUsed: Int
          _tmpAnnualUsed = _stmt.getLong(_columnIndexOfAnnualUsed).toInt()
          val _tmpAnnualPending: Int
          _tmpAnnualPending = _stmt.getLong(_columnIndexOfAnnualPending).toInt()
          val _tmpCasualTotal: Int
          _tmpCasualTotal = _stmt.getLong(_columnIndexOfCasualTotal).toInt()
          val _tmpCasualUsed: Int
          _tmpCasualUsed = _stmt.getLong(_columnIndexOfCasualUsed).toInt()
          val _tmpCasualPending: Int
          _tmpCasualPending = _stmt.getLong(_columnIndexOfCasualPending).toInt()
          val _tmpMedicalTotal: Int
          _tmpMedicalTotal = _stmt.getLong(_columnIndexOfMedicalTotal).toInt()
          val _tmpMedicalUsed: Int
          _tmpMedicalUsed = _stmt.getLong(_columnIndexOfMedicalUsed).toInt()
          val _tmpMedicalPending: Int
          _tmpMedicalPending = _stmt.getLong(_columnIndexOfMedicalPending).toInt()
          val _tmpNoPayUsed: Int
          _tmpNoPayUsed = _stmt.getLong(_columnIndexOfNoPayUsed).toInt()
          val _tmpNoPayPending: Int
          _tmpNoPayPending = _stmt.getLong(_columnIndexOfNoPayPending).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          _item = LeaveBalanceEntity(_tmpEmployeeId,_tmpAnnualTotal,_tmpAnnualUsed,_tmpAnnualPending,_tmpCasualTotal,_tmpCasualUsed,_tmpCasualPending,_tmpMedicalTotal,_tmpMedicalUsed,_tmpMedicalPending,_tmpNoPayUsed,_tmpNoPayPending,_tmpLastUpdated)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun addPendingDays(
    employeeId: String,
    leaveType: String,
    days: Int,
    timestamp: Long,
  ) {
    val _sql: String = """
        |
        |        UPDATE leave_balances
        |        SET annualPending  = CASE WHEN ? = 'ANNUAL'  THEN annualPending  + ? ELSE annualPending  END,
        |            casualPending  = CASE WHEN ? = 'CASUAL'  THEN casualPending  + ? ELSE casualPending  END,
        |            medicalPending = CASE WHEN ? = 'MEDICAL' THEN medicalPending + ? ELSE medicalPending END,
        |            noPayPending   = CASE WHEN ? = 'NOPAY'   THEN noPayPending   + ? ELSE noPayPending   END,
        |            lastUpdated    = ?
        |        WHERE employeeId = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 2
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 3
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 4
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 5
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 6
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 7
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 8
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 9
        _stmt.bindLong(_argIndex, timestamp)
        _argIndex = 10
        _stmt.bindText(_argIndex, employeeId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun approveDays(
    employeeId: String,
    leaveType: String,
    days: Int,
    timestamp: Long,
  ) {
    val _sql: String = """
        |
        |        UPDATE leave_balances
        |        SET annualPending  = CASE WHEN ? = 'ANNUAL'  THEN MAX(0, annualPending  - ?) ELSE annualPending  END,
        |            annualUsed     = CASE WHEN ? = 'ANNUAL'  THEN annualUsed  + ? ELSE annualUsed  END,
        |            casualPending  = CASE WHEN ? = 'CASUAL'  THEN MAX(0, casualPending  - ?) ELSE casualPending  END,
        |            casualUsed     = CASE WHEN ? = 'CASUAL'  THEN casualUsed  + ? ELSE casualUsed  END,
        |            medicalPending = CASE WHEN ? = 'MEDICAL' THEN MAX(0, medicalPending - ?) ELSE medicalPending END,
        |            medicalUsed    = CASE WHEN ? = 'MEDICAL' THEN medicalUsed + ? ELSE medicalUsed END,
        |            noPayPending   = CASE WHEN ? = 'NOPAY'   THEN MAX(0, noPayPending   - ?) ELSE noPayPending   END,
        |            noPayUsed      = CASE WHEN ? = 'NOPAY'   THEN noPayUsed   + ? ELSE noPayUsed   END,
        |            lastUpdated    = ?
        |        WHERE employeeId = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 2
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 3
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 4
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 5
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 6
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 7
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 8
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 9
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 10
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 11
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 12
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 13
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 14
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 15
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 16
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 17
        _stmt.bindLong(_argIndex, timestamp)
        _argIndex = 18
        _stmt.bindText(_argIndex, employeeId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun rejectDays(
    employeeId: String,
    leaveType: String,
    days: Int,
    timestamp: Long,
  ) {
    val _sql: String = """
        |
        |        UPDATE leave_balances
        |        SET annualPending  = CASE WHEN ? = 'ANNUAL'  THEN MAX(0, annualPending  - ?) ELSE annualPending  END,
        |            casualPending  = CASE WHEN ? = 'CASUAL'  THEN MAX(0, casualPending  - ?) ELSE casualPending  END,
        |            medicalPending = CASE WHEN ? = 'MEDICAL' THEN MAX(0, medicalPending - ?) ELSE medicalPending END,
        |            noPayPending   = CASE WHEN ? = 'NOPAY'   THEN MAX(0, noPayPending   - ?) ELSE noPayPending   END,
        |            lastUpdated    = ?
        |        WHERE employeeId = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 2
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 3
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 4
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 5
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 6
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 7
        _stmt.bindText(_argIndex, leaveType)
        _argIndex = 8
        _stmt.bindLong(_argIndex, days.toLong())
        _argIndex = 9
        _stmt.bindLong(_argIndex, timestamp)
        _argIndex = 10
        _stmt.bindText(_argIndex, employeeId)
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
