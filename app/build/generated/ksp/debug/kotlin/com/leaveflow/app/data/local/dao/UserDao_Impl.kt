package com.leaveflow.app.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.leaveflow.app.`data`.local.entity.UserEntity
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
public class UserDao_Impl(
  __db: RoomDatabase,
) : UserDao {
  private val __db: RoomDatabase

  private val __deleteAdapterOfUserEntity: EntityDeleteOrUpdateAdapter<UserEntity>

  private val __updateAdapterOfUserEntity: EntityDeleteOrUpdateAdapter<UserEntity>

  private val __upsertAdapterOfUserEntity: EntityUpsertAdapter<UserEntity>
  init {
    this.__db = __db
    this.__deleteAdapterOfUserEntity = object : EntityDeleteOrUpdateAdapter<UserEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `users` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfUserEntity = object : EntityDeleteOrUpdateAdapter<UserEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `users` SET `id` = ?,`name` = ?,`email` = ?,`passwordHash` = ?,`role` = ?,`department` = ?,`employeeId` = ?,`managerId` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.email)
        statement.bindText(4, entity.passwordHash)
        statement.bindText(5, entity.role)
        statement.bindText(6, entity.department)
        statement.bindText(7, entity.employeeId)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpManagerId)
        }
        statement.bindLong(9, entity.createdAt)
        statement.bindText(10, entity.id)
      }
    }
    this.__upsertAdapterOfUserEntity = EntityUpsertAdapter<UserEntity>(object : EntityInsertAdapter<UserEntity>() {
      protected override fun createQuery(): String = "INSERT INTO `users` (`id`,`name`,`email`,`passwordHash`,`role`,`department`,`employeeId`,`managerId`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.email)
        statement.bindText(4, entity.passwordHash)
        statement.bindText(5, entity.role)
        statement.bindText(6, entity.department)
        statement.bindText(7, entity.employeeId)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpManagerId)
        }
        statement.bindLong(9, entity.createdAt)
      }
    }, object : EntityDeleteOrUpdateAdapter<UserEntity>() {
      protected override fun createQuery(): String = "UPDATE `users` SET `id` = ?,`name` = ?,`email` = ?,`passwordHash` = ?,`role` = ?,`department` = ?,`employeeId` = ?,`managerId` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.email)
        statement.bindText(4, entity.passwordHash)
        statement.bindText(5, entity.role)
        statement.bindText(6, entity.department)
        statement.bindText(7, entity.employeeId)
        val _tmpManagerId: String? = entity.managerId
        if (_tmpManagerId == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpManagerId)
        }
        statement.bindLong(9, entity.createdAt)
        statement.bindText(10, entity.id)
      }
    })
  }

  public override suspend fun deleteUser(user: UserEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfUserEntity.handle(_connection, user)
  }

  public override suspend fun updateUser(user: UserEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfUserEntity.handle(_connection, user)
  }

  public override suspend fun insertUser(user: UserEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __upsertAdapterOfUserEntity.upsert(_connection, user)
  }

  public override suspend fun insertUsers(users: List<UserEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __upsertAdapterOfUserEntity.upsert(_connection, users)
  }

  public override suspend fun getUserByEmail(email: String): UserEntity? {
    val _sql: String = "SELECT * FROM users WHERE email = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: UserEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result = UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUserById(id: String): UserEntity? {
    val _sql: String = "SELECT * FROM users WHERE id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: UserEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result = UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUsersByRole(role: String): Flow<List<UserEntity>> {
    val _sql: String = "SELECT * FROM users WHERE role = ?"
    return createFlow(__db, false, arrayOf("users")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, role)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<UserEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: UserEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllUsers(): Flow<List<UserEntity>> {
    val _sql: String = "SELECT * FROM users"
    return createFlow(__db, false, arrayOf("users")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfEmployeeId: Int = getColumnIndexOrThrow(_stmt, "employeeId")
        val _columnIndexOfManagerId: Int = getColumnIndexOrThrow(_stmt, "managerId")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<UserEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: UserEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpEmployeeId: String
          _tmpEmployeeId = _stmt.getText(_columnIndexOfEmployeeId)
          val _tmpManagerId: String?
          if (_stmt.isNull(_columnIndexOfManagerId)) {
            _tmpManagerId = null
          } else {
            _tmpManagerId = _stmt.getText(_columnIndexOfManagerId)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpRole,_tmpDepartment,_tmpEmployeeId,_tmpManagerId,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUserCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM users"
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
