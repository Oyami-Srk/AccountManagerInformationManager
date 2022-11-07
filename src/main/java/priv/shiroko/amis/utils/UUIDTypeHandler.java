package priv.shiroko.amis.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.UUID;

@Slf4j
@MappedJdbcTypes(value = {JdbcType.CHAR})
@MappedTypes({UUID.class})
public class UUIDTypeHandler implements TypeHandler<UUID> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, UUID uuid, JdbcType jdbcType) throws SQLException {
        if (uuid == null)
            preparedStatement.setObject(i, null, Types.CHAR);
        else
            preparedStatement.setObject(i, uuid.toString(), Types.CHAR);
    }

    @Override
    public UUID getResult(ResultSet resultSet, String s) throws SQLException {
        return convertToUUID(resultSet.getString(s));
    }

    @Override
    public UUID getResult(ResultSet resultSet, int i) throws SQLException {
        return convertToUUID(resultSet.getString(i));
    }

    @Override
    public UUID getResult(CallableStatement callableStatement, int i) throws SQLException {
        return convertToUUID(callableStatement.getString(i));
    }

    private static UUID convertToUUID(String v) {
        if (v != null && !v.isEmpty()) {
            try {
                return UUID.fromString(v);
            } catch (IllegalArgumentException e) {
                log.error("Error when converting UUID from string: " + e.toString());
            }
        }
        return null;
    }
}
