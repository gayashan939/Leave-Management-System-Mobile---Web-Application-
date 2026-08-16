package com.leaveflow.app.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.leaveflow.app.`data`.local.entity.SyncQueueEntity
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
public class SyncQueueDao_Impl(
  __db: RoomDatabase,
) : SyncQueueDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSyncQueueEntity: EntityInsertAdapter<SyncQueueEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSyncQueueEntity = object : EntityInsertAdapter<SyncQueueEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `sync_queue` (`id`,`requestId`,`operation`,`payload`,`retryCount`,`lastAttempt`,`status`,`createdAt`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SyncQueueEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.requestId)
        statement.bindText(3, entity.operation)
        statement.bindText(4, entity.payload)
        statement.bindLong(5, entity.retryCount.toLong())
        val _tmpLastAttempt: Long? = entity.lastAttempt
        if (_tmpLastAttempt == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpLastAttempt)
        }
        statement.bindText(7, entity.status)
        statement.bindLong(8, entity.createdAt)
      }
    }
  }

  public override suspend fun enqueue(item: SyncQueueEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSyncQueueEntity.insert(_connection, item)
  }

  public override suspend fun getPendingItems(): List<SyncQueueEntity> {
    val _sql: String = "SELECT * FROM sync_queue WHERE status = 'PENDING_SYNC' OR status = 'FAILED' ORDER BY createdAt ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfRequestId: Int = getColumnIndexOrThrow(_stmt, "requestId")
        val _columnIndexOfOperation: Int = getColumnIndexOrThrow(_stmt, "operation")
        val _columnIndexOfPayload: Int = getColumnIndexOrThrow(_stmt, "payload")
        val _columnIndexOfRetryCount: Int = getColumnIndexOrThrow(_stmt, "retryCount")
        val _columnIndexOfLastAttempt: Int = getColumnIndexOrThrow(_stmt, "lastAttempt")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<SyncQueueEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SyncQueueEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpRequestId: String
          _tmpRequestId = _stmt.getText(_columnIndexOfRequestId)
          val _tmpOperation: String
          _tmpOperation = _stmt.getText(_columnIndexOfOperation)
          val _tmpPayload: String
          _tmpPayload = _stmt.getText(_columnIndexOfPayload)
          val _tmpRetryCount: Int
          _tmpRetryCount = _stmt.getLong(_columnIndexOfRetryCount).toInt()
          val _tmpLastAttempt: Long?
          if (_stmt.isNull(_columnIndexOfLastAttempt)) {
            _tmpLastAttempt = null
          } else {
            _tmpLastAttempt = _stmt.getLong(_columnIndexOfLastAttempt)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = SyncQueueEntity(_tmpId,_tmpRequestId,_tmpOperation,_tmpPayload,_tmpRetryCount,_tmpLastAttempt,_tmpStatus,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllItems(): Flow<List<SyncQueueEntity>> {
    val _sql: String = "SELECT * FROM sync_queue ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("sync_queue")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfRequestId: Int = getColumnIndexOrThrow(_stmt, "requestId")
        val _columnIndexOfOperation: Int = getColumnIndexOrThrow(_stmt, "operation")
        val _columnIndexOfPayload: Int = getColumnIndexOrThrow(_stmt, "payload")
        val _columnIndexOfRetryCount: Int = getColumnIndexOrThrow(_stmt, "retryCount")
        val _columnIndexOfLastAttempt: Int = getColumnIndexOrThrow(_stmt, "lastAttempt")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<SyncQueueEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SyncQueueEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpRequestId: String
          _tmpRequestId = _stmt.getText(_columnIndexOfRequestId)
          val _tmpOperation: String
          _tmpOperation = _stmt.getText(_columnIndexOfOperation)
          val _tmpPayload: String
          _tmpPayload = _stmt.getText(_columnIndexOfPayload)
          val _tmpRetryCount: Int
          _tmpRetryCount = _stmt.getLong(_columnIndexOfRetryCount).toInt()
          val _tmpLastAttempt: Long?
          if (_stmt.isNull(_columnIndexOfLastAttempt)) {
            _tmpLastAttempt = null
          } else {
            _tmpLastAttempt = _stmt.getLong(_columnIndexOfLastAttempt)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = SyncQueueEntity(_tmpId,_tmpRequestId,_tmpOperation,_tmpPayload,_tmpRetryCount,_tmpLastAttempt,_tmpStatus,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getPendingCount(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM sync_queue WHERE status = 'PENDING_SYNC' OR status = 'FAILED'"
    return createFlow(__db, false, arrayOf("sync_queue")) { _connection ->
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

  public override suspend fun getPendingCountOnce(): Int {
    val _sql: String = "SELECT COUNT(*) FROM sync_queue WHERE status = 'PENDING_SYNC' OR status = 'FAILED'"
    return performSuspending(__db, true, false) { _connection ->
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

  public override suspend fun updateStatus(
    id: String,
    status: String,
    timestamp: Long,
  ) {
    val _sql: String = "UPDATE sync_queue SET status = ?, retryCount = retryCount + 1, lastAttempt = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        _argIndex = 2
        _stmt.bindLong(_argIndex, timestamp)
        _argIndex = 3
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearSynced() {
    val _sql: String = "DELETE FROM sync_queue WHERE status = 'SYNCED'"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun removeByRequestId(requestId: String) {
    val _sql: String = "DELETE FROM sync_queue WHERE requestId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, requestId)
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
