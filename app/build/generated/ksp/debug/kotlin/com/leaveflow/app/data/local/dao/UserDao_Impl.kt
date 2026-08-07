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
import androidx.room.util.createCancellationSignal
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.query
import androidx.sqlite.db.SupportSQLiteStatement
import com.leaveflow.app.`data`.local.entity.UserEntity
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
public class UserDao_Impl(
  __db: RoomDatabase,
) : UserDao {
  private val __db: RoomDatabase

  private val __insertionAdapterOfUserEntity: EntityInsertionAdapter<UserEntity>

  private val __insertionAdapterOfUserEntity_1: EntityInsertionAdapter<UserEntity>

  private val __deletionAdapterOfUserEntity: EntityDeletionOrUpdateAdapter<UserEntity>

  private val __updateAdapterOfUserEntity: EntityDeletionOrUpdateAdapter<UserEntity>
  init {
    this.__db = __db
    this.__insertionAdapterOfUserEntity = object : EntityInsertionAdapter<UserEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `users` (`id`,`name`,`email`,`passwordHash`,`role`,`department`,`employeeId`,`managerId`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: UserEntity) {
        statement.bindString(1, entity.id)
        statement.bindString(2, entity.name)
        statement.bindString(3, entity.email)
        statement.bindString(4, entity.passwordHash)
        statement.bindString(5, entity.role)
        statement.bindString(6, entity.department)
        statement.bindString(7, entity.employeeId)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(8)
        } else {
          statement.bindString(8, _tmpManagerId)
        }
        statement.bindLong(9, entity.createdAt)
      }
    }
    this.__insertionAdapterOfUserEntity_1 = object : EntityInsertionAdapter<UserEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `users` (`id`,`name`,`email`,`passwordHash`,`role`,`department`,`employeeId`,`managerId`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: UserEntity) {
        statement.bindString(1, entity.id)
        statement.bindString(2, entity.name)
        statement.bindString(3, entity.email)
        statement.bindString(4, entity.passwordHash)
        statement.bindString(5, entity.role)
        statement.bindString(6, entity.department)
        statement.bindString(7, entity.employeeId)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(8)
        } else {
          statement.bindString(8, _tmpManagerId)
        }
        statement.bindLong(9, entity.createdAt)
      }
    }
    this.__deletionAdapterOfUserEntity = object : EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      protected override fun createQuery(): String = "DELETE FROM `users` WHERE `id` = ?"

      protected override fun bind(statement: SupportSQLiteStatement, entity: UserEntity) {
        statement.bindString(1, entity.id)
      }
    }
    this.__updateAdapterOfUserEntity = object : EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `users` SET `id` = ?,`name` = ?,`email` = ?,`passwordHash` = ?,`role` = ?,`department` = ?,`employeeId` = ?,`managerId` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SupportSQLiteStatement, entity: UserEntity) {
        statement.bindString(1, entity.id)
        statement.bindString(2, entity.name)
        statement.bindString(3, entity.email)
        statement.bindString(4, entity.passwordHash)
        statement.bindString(5, entity.role)
        statement.bindString(6, entity.department)
        statement.bindString(7, entity.employeeId)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(8)
        } else {
          statement.bindString(8, _tmpManagerId)
        }
        statement.bindLong(9, entity.createdAt)
        statement.bindString(10, entity.id)
      }
    }
  }

  public override suspend fun insertUser(user: UserEntity): Unit = CoroutinesRoom.execute(__db,
      true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __insertionAdapterOfUserEntity.insert(user)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun insertUsers(users: List<UserEntity>): Unit =
      CoroutinesRoom.execute(__db, true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __insertionAdapterOfUserEntity_1.insert(users)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun deleteUser(user: UserEntity): Unit = CoroutinesRoom.execute(__db,
      true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __deletionAdapterOfUserEntity.handle(user)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun updateUser(user: UserEntity): Unit = CoroutinesRoom.execute(__db,
      true, object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __updateAdapterOfUserEntity.handle(user)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun getUserByEmail(email: String): UserEntity? {
    val _sql: String = "SELECT * FROM users WHERE email = ? LIMIT 1"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, email)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<UserEntity?> {
      public override fun call(): UserEntity? {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfEmail: Int = getColumnIndexOrThrow(_cursor, "email")
          val _cursorIndexOfPasswordHash: Int = getColumnIndexOrThrow(_cursor, "passwordHash")
          val _cursorIndexOfRole: Int = getColumnIndexOrThrow(_cursor, "role")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _result: UserEntity?
          if (_cursor.moveToFirst()) {
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpEmail: String
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail)
            val _tmpPasswordHash: String
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash)
            val _tmpRole: String
            _tmpRole = _cursor.getString(_cursorIndexOfRole)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            _result =
                UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
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

  public override suspend fun getUserById(id: String): UserEntity? {
    val _sql: String = "SELECT * FROM users WHERE id = ? LIMIT 1"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, id)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<UserEntity?> {
      public override fun call(): UserEntity? {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfEmail: Int = getColumnIndexOrThrow(_cursor, "email")
          val _cursorIndexOfPasswordHash: Int = getColumnIndexOrThrow(_cursor, "passwordHash")
          val _cursorIndexOfRole: Int = getColumnIndexOrThrow(_cursor, "role")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _result: UserEntity?
          if (_cursor.moveToFirst()) {
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpEmail: String
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail)
            val _tmpPasswordHash: String
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash)
            val _tmpRole: String
            _tmpRole = _cursor.getString(_cursorIndexOfRole)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            _result =
                UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
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

  public override fun getUsersByRole(role: String): Flow<List<UserEntity>> {
    val _sql: String = "SELECT * FROM users WHERE role = ?"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, role)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("users"), object :
        Callable<List<UserEntity>> {
      public override fun call(): List<UserEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfEmail: Int = getColumnIndexOrThrow(_cursor, "email")
          val _cursorIndexOfPasswordHash: Int = getColumnIndexOrThrow(_cursor, "passwordHash")
          val _cursorIndexOfRole: Int = getColumnIndexOrThrow(_cursor, "role")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _result: MutableList<UserEntity> = ArrayList<UserEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: UserEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpEmail: String
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail)
            val _tmpPasswordHash: String
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash)
            val _tmpRole: String
            _tmpRole = _cursor.getString(_cursorIndexOfRole)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            _item =
                UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
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

  public override fun getAllUsers(): Flow<List<UserEntity>> {
    val _sql: String = "SELECT * FROM users"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("users"), object :
        Callable<List<UserEntity>> {
      public override fun call(): List<UserEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfEmail: Int = getColumnIndexOrThrow(_cursor, "email")
          val _cursorIndexOfPasswordHash: Int = getColumnIndexOrThrow(_cursor, "passwordHash")
          val _cursorIndexOfRole: Int = getColumnIndexOrThrow(_cursor, "role")
          val _cursorIndexOfDepartment: Int = getColumnIndexOrThrow(_cursor, "department")
          val _cursorIndexOfEmployeeId: Int = getColumnIndexOrThrow(_cursor, "employeeId")
          val _cursorIndexOfManagerId: Int = getColumnIndexOrThrow(_cursor, "managerId")
          val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_cursor, "createdAt")
          val _result: MutableList<UserEntity> = ArrayList<UserEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: UserEntity
            val _tmpId: String
            _tmpId = _cursor.getString(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpEmail: String
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail)
            val _tmpPasswordHash: String
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash)
            val _tmpRole: String
            _tmpRole = _cursor.getString(_cursorIndexOfRole)
            val _tmpDepartment: String
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment)
            val _tmpEmployeeId: String
            _tmpEmployeeId = _cursor.getString(_cursorIndexOfEmployeeId)
            val _tmpManagerId: String?
            if (_cursor.isNull(_cursorIndexOfManagerId)) {
              _tmpManagerId = null
            } else {
              _tmpManagerId = _cursor.getString(_cursorIndexOfManagerId)
            }
            val _tmpCreatedAt: Long
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt)
            _item =
                UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
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

  public override suspend fun getUserCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM users"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<Int> {
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
          _statement.release()
        }
      }
    })
  }

  public companion object {
    @JvmStatic
    public fun getRequiredConverters(): List<Class<*>> = emptyList()
  }
}
