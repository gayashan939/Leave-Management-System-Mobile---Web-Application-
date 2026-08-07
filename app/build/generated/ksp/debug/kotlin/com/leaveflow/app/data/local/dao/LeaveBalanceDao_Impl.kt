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
import com.leaveflow.app.`data`.local.entity.LeaveBalanceEntity
import java.lang.Class
import java.util.ArrayList
import java.util.concurrent.Callable
import javax.`annotation`.processing.Generated
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
public class LeaveBalanceDao_Impl(
  __db: RoomDatabase,
) : LeaveBalanceDao {
  private val __db: RoomDatabase

  private val __insertionAdapterOfLeaveBalanceEntity: EntityInsertionAdapter<LeaveBalanceEntity>

  private val __insertionAdapterOfLeaveBalanceEntity_1: EntityInsertionAdapter<LeaveBalanceEntity>

  private val __updateAdapterOfLeaveBalanceEntity: EntityDeletionOrUpdateAdapter<LeaveBalanceEntity>

  private val __preparedStmtOfAddPendingDays: SharedSQLiteStatement

  private val __preparedStmtOfApproveDays: SharedSQLiteStatement

  private val __preparedStmtOfRejectDays: SharedSQLiteStatement
  init {
    this.__db = __db
    this.__insertionAdapterOfLeaveBalanceEntity = object :
        EntityInsertionAdapter<LeaveBalanceEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `leave_balances` (`employeeId`,`annualTotal`,`annualUsed`,`annualPending`,`casualTotal`,`casualUsed`,`casualPending`,`medicalTotal`,`medicalUsed`,`medicalPending`,`noPayUsed`,`noPayPending`,`lastUpdated`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: LeaveBalanceEntity) {
        statement.bindString(1, entity.employeeId)
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
    this.__insertionAdapterOfLeaveBalanceEntity_1 = object :
        EntityInsertionAdapter<LeaveBalanceEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `leave_balances` (`employeeId`,`annualTotal`,`annualUsed`,`annualPending`,`casualTotal`,`casualUsed`,`casualPending`,`medicalTotal`,`medicalUsed`,`medicalPending`,`noPayUsed`,`noPayPending`,`lastUpdated`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: LeaveBalanceEntity) {
        statement.bindString(1, entity.employeeId)
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
    this.__updateAdapterOfLeaveBalanceEntity = object :
        EntityDeletionOrUpdateAdapter<LeaveBalanceEntity>(__db) {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `leave_balances` SET `employeeId` = ?,`annualTotal` = ?,`annualUsed` = ?,`annualPending` = ?,`casualTotal` = ?,`casualUsed` = ?,`casualPending` = ?,`medicalTotal` = ?,`medicalUsed` = ?,`medicalPending` = ?,`noPayUsed` = ?,`noPayPending` = ?,`lastUpdated` = ? WHERE `employeeId` = ?"

      protected override fun bind(statement: SupportSQLiteStatement, entity: LeaveBalanceEntity) {
        statement.bindString(1, entity.employeeId)
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
        statement.bindString(14, entity.employeeId)
      }
    }
    this.__preparedStmtOfAddPendingDays = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = """
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
        return _query
      }
    }
    this.__preparedStmtOfApproveDays = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = """
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
        return _query
      }
    }
    this.__preparedStmtOfRejectDays = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = """
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
        return _query
      }
    }
  }

  public override suspend fun insertBalance(balance: LeaveBalanceEntity): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __insertionAdapterOfLeaveBalanceEntity.insert(balance)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun insertBalances(balances: List<LeaveBalanceEntity>): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __insertionAdapterOfLeaveBalanceEntity_1.insert(balances)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun updateBalance(balance: LeaveBalanceEntity): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __updateAdapterOfLeaveBalanceEntity.handle(balance)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun addPendingDays(
    employeeId: String,
    leaveType: String,
    days: Int,
    timestamp: Long,
  ): Unit = CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfAddPendingDays.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 2
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 3
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 4
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 5
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 6
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 7
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 8
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 9
      _stmt.bindLong(_argIndex, timestamp)
      _argIndex = 10
      _stmt.bindString(_argIndex, employeeId)
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfAddPendingDays.release(_stmt)
      }
    }
  })

  public override suspend fun approveDays(
    employeeId: String,
    leaveType: String,
    days: Int,
    timestamp: Long,
  ): Unit = CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfApproveDays.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 2
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 3
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 4
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 5
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 6
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 7
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 8
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 9
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 10
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 11
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 12
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 13
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 14
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 15
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 16
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 17
      _stmt.bindLong(_argIndex, timestamp)
      _argIndex = 18
      _stmt.bindString(_argIndex, employeeId)
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfApproveDays.release(_stmt)
      }
    }
  })

  public override suspend fun rejectDays(
    employeeId: String,
    leaveType: String,
    days: Int,
    timestamp: Long,
  ): Unit = CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfRejectDays.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 2
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 3
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 4
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 5
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 6
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 7
      _stmt.bindString(_argIndex, leaveType)
      _argIndex = 8
      _stmt.bindLong(_argIndex, days.toLong())
      _argIndex = 9
      _stmt.bindLong(_argIndex, timestamp)
      _argIndex = 10
      _stmt.bindString(_argIndex, employeeId)
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfRejectDays.release(_stmt)
      }
    }
  })

  public override fun getBalanceByEmployee(employeeId: String): Flow<LeaveBalanceEntity?> {
    val _sql: String = "SELECT * FROM leave_balances WHERE employeeId = ? LIMIT 1"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, employeeId)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_balances"), object :
        Callable<LeaveBalanceEntity?> {
      public override fun call(): LeaveBalanceEntity? {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfAnnualTotal: Int = getColumnIndexOrThrow(_cursor, "annualTotal")
          val _cursorIndexOfAnnualUsed: Int = getColumnIndexOrThrow(_cursor, "annualUsed")
          val _cursorIndexOfAnnualPending: Int = getColumnIndexOrThrow(_cursor, "annualPending")
          val _cursorIndexOfCasualTotal: Int = getColumnIndexOrThrow(_cursor, "casualTotal")
          val _cursorIndexOfCasualUsed: Int = getColumnIndexOrThrow(_cursor, "casualUsed")
          val _cursorIndexOfCasualPending: Int = getColumnIndexOrThrow(_cursor, "casualPending")
          val _cursorIndexOfMedicalTotal: Int = getColumnIndexOrThrow(_cursor, "medicalTotal")
          val _cursorIndexOfMedicalUsed: Int = getColumnIndexOrThrow(_cursor, "medicalUsed")
          val _cursorIndexOfMedicalPending: Int = getColumnIndexOrThrow(_cursor, "medicalPending")
          val _cursorIndexOfNoPayUsed: Int = getColumnIndexOrThrow(_cursor, "noPayUsed")
          val _cursorIndexOfNoPayPending: Int = getColumnIndexOrThrow(_cursor, "noPayPending")
          val _cursorIndexOfLastUpdated: Int = getColumnIndexOrThrow(_cursor, "lastUpdated")
          val _result: LeaveBalanceEntity?
          if (_cursor.moveToFirst()) {
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpAnnualTotal: Int
            _tmpAnnualTotal = _cursor.getInt(_cursorIndexOfAnnualTotal)
            val _tmpAnnualUsed: Int
            _tmpAnnualUsed = _cursor.getInt(_cursorIndexOfAnnualUsed)
            val _tmpAnnualPending: Int
            _tmpAnnualPending = _cursor.getInt(_cursorIndexOfAnnualPending)
            val _tmpCasualTotal: Int
            _tmpCasualTotal = _cursor.getInt(_cursorIndexOfCasualTotal)
            val _tmpCasualUsed: Int
            _tmpCasualUsed = _cursor.getInt(_cursorIndexOfCasualUsed)
            val _tmpCasualPending: Int
            _tmpCasualPending = _cursor.getInt(_cursorIndexOfCasualPending)
            val _tmpMedicalTotal: Int
            _tmpMedicalTotal = _cursor.getInt(_cursorIndexOfMedicalTotal)
            val _tmpMedicalUsed: Int
            _tmpMedicalUsed = _cursor.getInt(_cursorIndexOfMedicalUsed)
            val _tmpMedicalPending: Int
            _tmpMedicalPending = _cursor.getInt(_cursorIndexOfMedicalPending)
            val _tmpNoPayUsed: Int
            _tmpNoPayUsed = _cursor.getInt(_cursorIndexOfNoPayUsed)
            val _tmpNoPayPending: Int
            _tmpNoPayPending = _cursor.getInt(_cursorIndexOfNoPayPending)
            val _tmpLastUpdated: Long
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated)
            _result =
                LeaveBalanceEntity(_tmpEmployeeId,_tmpAnnualTotal,_tmpAnnualUsed,_tmpAnnualPending,_tmpCasualTotal,_tmpCasualUsed,_tmpCasualPending,_tmpMedicalTotal,_tmpMedicalUsed,_tmpMedicalPending,_tmpNoPayUsed,_tmpNoPayPending,_tmpLastUpdated)
          } else {
            _result = null
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

  public override suspend fun getBalanceByEmployeeOnce(employeeId: String): LeaveBalanceEntity? {
    val _sql: String = "SELECT * FROM leave_balances WHERE employeeId = ? LIMIT 1"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, employeeId)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<LeaveBalanceEntity?> {
      public override fun call(): LeaveBalanceEntity? {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfAnnualTotal: Int = getColumnIndexOrThrow(_cursor, "annualTotal")
          val _cursorIndexOfAnnualUsed: Int = getColumnIndexOrThrow(_cursor, "annualUsed")
          val _cursorIndexOfAnnualPending: Int = getColumnIndexOrThrow(_cursor, "annualPending")
          val _cursorIndexOfCasualTotal: Int = getColumnIndexOrThrow(_cursor, "casualTotal")
          val _cursorIndexOfCasualUsed: Int = getColumnIndexOrThrow(_cursor, "casualUsed")
          val _cursorIndexOfCasualPending: Int = getColumnIndexOrThrow(_cursor, "casualPending")
          val _cursorIndexOfMedicalTotal: Int = getColumnIndexOrThrow(_cursor, "medicalTotal")
          val _cursorIndexOfMedicalUsed: Int = getColumnIndexOrThrow(_cursor, "medicalUsed")
          val _cursorIndexOfMedicalPending: Int = getColumnIndexOrThrow(_cursor, "medicalPending")
          val _cursorIndexOfNoPayUsed: Int = getColumnIndexOrThrow(_cursor, "noPayUsed")
          val _cursorIndexOfNoPayPending: Int = getColumnIndexOrThrow(_cursor, "noPayPending")
          val _cursorIndexOfLastUpdated: Int = getColumnIndexOrThrow(_cursor, "lastUpdated")
          val _result: LeaveBalanceEntity?
          if (_cursor.moveToFirst()) {
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpAnnualTotal: Int
            _tmpAnnualTotal = _cursor.getInt(_cursorIndexOfAnnualTotal)
            val _tmpAnnualUsed: Int
            _tmpAnnualUsed = _cursor.getInt(_cursorIndexOfAnnualUsed)
            val _tmpAnnualPending: Int
            _tmpAnnualPending = _cursor.getInt(_cursorIndexOfAnnualPending)
            val _tmpCasualTotal: Int
            _tmpCasualTotal = _cursor.getInt(_cursorIndexOfCasualTotal)
            val _tmpCasualUsed: Int
            _tmpCasualUsed = _cursor.getInt(_cursorIndexOfCasualUsed)
            val _tmpCasualPending: Int
            _tmpCasualPending = _cursor.getInt(_cursorIndexOfCasualPending)
            val _tmpMedicalTotal: Int
            _tmpMedicalTotal = _cursor.getInt(_cursorIndexOfMedicalTotal)
            val _tmpMedicalUsed: Int
            _tmpMedicalUsed = _cursor.getInt(_cursorIndexOfMedicalUsed)
            val _tmpMedicalPending: Int
            _tmpMedicalPending = _cursor.getInt(_cursorIndexOfMedicalPending)
            val _tmpNoPayUsed: Int
            _tmpNoPayUsed = _cursor.getInt(_cursorIndexOfNoPayUsed)
            val _tmpNoPayPending: Int
            _tmpNoPayPending = _cursor.getInt(_cursorIndexOfNoPayPending)
            val _tmpLastUpdated: Long
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated)
            _result =
                LeaveBalanceEntity(_tmpEmployeeId,_tmpAnnualTotal,_tmpAnnualUsed,_tmpAnnualPending,_tmpCasualTotal,_tmpCasualUsed,_tmpCasualPending,_tmpMedicalTotal,_tmpMedicalUsed,_tmpMedicalPending,_tmpNoPayUsed,_tmpNoPayPending,_tmpLastUpdated)
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

  public override fun getAllBalances(): Flow<List<LeaveBalanceEntity>> {
    val _sql: String = "SELECT * FROM leave_balances"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("leave_balances"), object :
        Callable<List<LeaveBalanceEntity>> {
      public override fun call(): List<LeaveBalanceEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfAnnualTotal: Int = getColumnIndexOrThrow(_cursor, "annualTotal")
          val _cursorIndexOfAnnualUsed: Int = getColumnIndexOrThrow(_cursor, "annualUsed")
          val _cursorIndexOfAnnualPending: Int = getColumnIndexOrThrow(_cursor, "annualPending")
          val _cursorIndexOfCasualTotal: Int = getColumnIndexOrThrow(_cursor, "casualTotal")
          val _cursorIndexOfCasualUsed: Int = getColumnIndexOrThrow(_cursor, "casualUsed")
          val _cursorIndexOfCasualPending: Int = getColumnIndexOrThrow(_cursor, "casualPending")
          val _cursorIndexOfMedicalTotal: Int = getColumnIndexOrThrow(_cursor, "medicalTotal")
          val _cursorIndexOfMedicalUsed: Int = getColumnIndexOrThrow(_cursor, "medicalUsed")
          val _cursorIndexOfMedicalPending: Int = getColumnIndexOrThrow(_cursor, "medicalPending")
          val _cursorIndexOfNoPayUsed: Int = getColumnIndexOrThrow(_cursor, "noPayUsed")
          val _cursorIndexOfNoPayPending: Int = getColumnIndexOrThrow(_cursor, "noPayPending")
          val _cursorIndexOfLastUpdated: Int = getColumnIndexOrThrow(_cursor, "lastUpdated")
          val _result: MutableList<LeaveBalanceEntity> =
              ArrayList<LeaveBalanceEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: LeaveBalanceEntity
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpAnnualTotal: Int
            _tmpAnnualTotal = _cursor.getInt(_cursorIndexOfAnnualTotal)
            val _tmpAnnualUsed: Int
            _tmpAnnualUsed = _cursor.getInt(_cursorIndexOfAnnualUsed)
            val _tmpAnnualPending: Int
            _tmpAnnualPending = _cursor.getInt(_cursorIndexOfAnnualPending)
            val _tmpCasualTotal: Int
            _tmpCasualTotal = _cursor.getInt(_cursorIndexOfCasualTotal)
            val _tmpCasualUsed: Int
            _tmpCasualUsed = _cursor.getInt(_cursorIndexOfCasualUsed)
            val _tmpCasualPending: Int
            _tmpCasualPending = _cursor.getInt(_cursorIndexOfCasualPending)
            val _tmpMedicalTotal: Int
            _tmpMedicalTotal = _cursor.getInt(_cursorIndexOfMedicalTotal)
            val _tmpMedicalUsed: Int
            _tmpMedicalUsed = _cursor.getInt(_cursorIndexOfMedicalUsed)
            val _tmpMedicalPending: Int
            _tmpMedicalPending = _cursor.getInt(_cursorIndexOfMedicalPending)
            val _tmpNoPayUsed: Int
            _tmpNoPayUsed = _cursor.getInt(_cursorIndexOfNoPayUsed)
            val _tmpNoPayPending: Int
            _tmpNoPayPending = _cursor.getInt(_cursorIndexOfNoPayPending)
            val _tmpLastUpdated: Long
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated)
            _item =
                LeaveBalanceEntity(_tmpEmployeeId,_tmpAnnualTotal,_tmpAnnualUsed,_tmpAnnualPending,_tmpCasualTotal,_tmpCasualUsed,_tmpCasualPending,_tmpMedicalTotal,_tmpMedicalUsed,_tmpMedicalPending,_tmpNoPayUsed,_tmpNoPayPending,_tmpLastUpdated)
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

  public companion object {
    @JvmStatic
    public fun getRequiredConverters(): List<Class<*>> = emptyList()
  }
}
