package com.leaveflow.app.`data`.local.dao

import android.database.Cursor
import android.os.CancellationSignal
import androidx.room.CoroutinesRoom
import androidx.room.CoroutinesRoom.Companion.execute
import androidx.room.EntityInsertionAdapter
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.RoomSQLiteQuery.Companion.acquire
import androidx.room.SharedSQLiteStatement
import androidx.room.util.createCancellationSignal
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.query
import androidx.sqlite.db.SupportSQLiteStatement
import com.leaveflow.app.`data`.local.entity.SyncQueueEntity
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
public class SyncQueueDao_Impl(
  __db: RoomDatabase,
) : SyncQueueDao {
  private val __db: RoomDatabase

  private val __insertionAdapterOfSyncQueueEntity: EntityInsertionAdapter<SyncQueueEntity>

  private val __preparedStmtOfUpdateStatus: SharedSQLiteStatement

  private val __preparedStmtOfClearSynced: SharedSQLiteStatement

  private val __preparedStmtOfRemoveByRequestId: SharedSQLiteStatement
  init {
    this.__db = __db
    this.__insertionAdapterOfSyncQueueEntity = object :
        EntityInsertionAdapter<SyncQueueEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `sync_queue` (`id`,`requestId`,`operation`,`payload`,`retryCount`,`lastAttempt`,`status`,`createdAt`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: SyncQueueEntity) {
        statement.bindString(1, entity.id)
        statement.bindString(2, entity.requestId)
        statement.bindString(3, entity.operation)
        statement.bindString(4, entity.payload)
        statement.bindLong(5, entity.retryCount.toLong())
        val _tmpLastAttempt: Long? = entity.lastAttempt
        if (_tmpLastAttempt == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpLastAttempt)
        }
        statement.bindString(7, entity.status)
        statement.bindLong(8, entity.createdAt)
      }
    }
    this.__preparedStmtOfUpdateStatus = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String =
            "UPDATE sync_queue SET status = ?, retryCount = retryCount + 1, lastAttempt = ? WHERE id = ?"
        return _query
      }
    }
    this.__preparedStmtOfClearSynced = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = "DELETE FROM sync_queue WHERE status = 'SYNCED'"
        return _query
      }
    }
    this.__preparedStmtOfRemoveByRequestId = object : SharedSQLiteStatement(__db) {
      public override fun createQuery(): String {
        val _query: String = "DELETE FROM sync_queue WHERE requestId = ?"
        return _query
      }
    }
  }

  public override suspend fun enqueue(item: SyncQueueEntity): Unit = CoroutinesRoom.execute(__db,
      true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __insertionAdapterOfSyncQueueEntity.insert(item)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun updateStatus(
    id: String,
    status: String,
    timestamp: Long,
  ): Unit = CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfUpdateStatus.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, status)
      _argIndex = 2
      _stmt.bindLong(_argIndex, timestamp)
      _argIndex = 3
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
        __preparedStmtOfUpdateStatus.release(_stmt)
      }
    }
  })

  public override suspend fun clearSynced(): Unit = CoroutinesRoom.execute(__db, true, object :
      Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfClearSynced.acquire()
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfClearSynced.release(_stmt)
      }
    }
  })

  public override suspend fun removeByRequestId(requestId: String): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      val _stmt: SupportSQLiteStatement = __preparedStmtOfRemoveByRequestId.acquire()
      var _argIndex: Int = 1
      _stmt.bindString(_argIndex, requestId)
      try {
        __db.beginTransaction()
        try {
          _stmt.executeUpdateDelete()
          __db.setTransactionSuccessful()
        } finally {
          __db.endTransaction()
        }
      } finally {
        __preparedStmtOfRemoveByRequestId.release(_stmt)
      }
    }
  })

  public override suspend fun getPendingItems(): List<SyncQueueEntity> {
    val _sql: String =
        "SELECT * FROM sync_queue WHERE status = 'PENDING_SYNC' OR status = 'FAILED' ORDER BY createdAt ASC"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<List<SyncQueueEntity>> {
      public override fun call(): List<SyncQueueEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfRequestId: Int = getColumnIndexOrThrow(_cursor, "requestId")
          val _cursorIndexOfOperation: Int = getColumnIndexOrThrow(_cursor, "operation")
          val _cursorIndexOfPayload: Int = getColumnIndexOrThrow(_cursor, "payload")
          val _cursorIndexOfRetryCount: Int = getColumnIndexOrThrow(_cursor, "retryCount")
          val _cursorIndexOfLastAttempt: Int = getColumnIndexOrThrow(_cursor, "lastAttempt")
          val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_cursor, "status")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _result: MutableList<SyncQueueEntity> = ArrayList<SyncQueueEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: SyncQueueEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpRequestId: String
            _tmpRequestId = _cursor.getString(_cursorIndexOfRequestId)
            val _tmpOperation: String
            _tmpOperation = _cursor.getString(_cursorIndexOfOperation)
            val _tmpPayload: String
            _tmpPayload = _cursor.getString(_cursorIndexOfPayload)
            val _tmpRetryCount: Int
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount)
            val _tmpLastAttempt: Long?
            if (_cursor.isNull(_cursorIndexOfLastAttempt)) {
              _tmpLastAttempt = null
            } else {
              _tmpLastAttempt = _cursor.getLong(_cursorIndexOfLastAttempt)
            }
            val _tmpStatus: String
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus)
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            _item =
                SyncQueueEntity(_tmpId,_tmpRequestId,_tmpOperation,_tmpPayload,_tmpRetryCount,_tmpLastAttempt,_tmpStatus,_tmpCreatedAt)
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

  public override fun getAllItems(): Flow<List<SyncQueueEntity>> {
    val _sql: String = "SELECT * FROM sync_queue ORDER BY createdAt DESC"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("sync_queue"), object :
        Callable<List<SyncQueueEntity>> {
      public override fun call(): List<SyncQueueEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfRequestId: Int = getColumnIndexOrThrow(_cursor, "requestId")
          val _cursorIndexOfOperation: Int = getColumnIndexOrThrow(_cursor, "operation")
          val _cursorIndexOfPayload: Int = getColumnIndexOrThrow(_cursor, "payload")
          val _cursorIndexOfRetryCount: Int = getColumnIndexOrThrow(_cursor, "retryCount")
          val _cursorIndexOfLastAttempt: Int = getColumnIndexOrThrow(_cursor, "lastAttempt")
          val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_cursor, "status")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _result: MutableList<SyncQueueEntity> = ArrayList<SyncQueueEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: SyncQueueEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpRequestId: String
            _tmpRequestId = _cursor.getString(_cursorIndexOfRequestId)
            val _tmpOperation: String
            _tmpOperation = _cursor.getString(_cursorIndexOfOperation)
            val _tmpPayload: String
            _tmpPayload = _cursor.getString(_cursorIndexOfPayload)
            val _tmpRetryCount: Int
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount)
            val _tmpLastAttempt: Long?
            if (_cursor.isNull(_cursorIndexOfLastAttempt)) {
              _tmpLastAttempt = null
            } else {
              _tmpLastAttempt = _cursor.getLong(_cursorIndexOfLastAttempt)
            }
            val _tmpStatus: String
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus)
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            _item =
                SyncQueueEntity(_tmpId,_tmpRequestId,_tmpOperation,_tmpPayload,_tmpRetryCount,_tmpLastAttempt,_tmpStatus,_tmpCreatedAt)
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

  public override fun getPendingCount(): Flow<Int> {
    val _sql: String =
        "SELECT COUNT(*) FROM sync_queue WHERE status = 'PENDING_SYNC' OR status = 'FAILED'"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("sync_queue"), object : Callable<Int> {
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
