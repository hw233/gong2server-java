package com.gamejelly.gong2.dbs.dao.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.hadoit.game.common.framework.dao.CommonJdbcDaoSupport;
import com.hadoit.game.common.framework.dao.convert.ColumnConvert;
import com.hadoit.game.common.framework.dao.domain.TableMeta;
import com.hadoit.game.common.framework.dao.domain.TableMetaHelper;
import com.hadoit.game.common.framework.dao.utils.BaseUtils;
import com.hadoit.game.common.framework.dao.utils.CommonJdbcDataAccessException;
import com.hadoit.game.common.framework.dao.utils.DBUtils;

public class AutoMetaDaoSupport extends CommonJdbcDaoSupport {
	private Map<Class<?>, TableMeta<?>> tableMetaMap = new HashMap<Class<?>, TableMeta<?>>();

	public void registTableMeta(Class<?> metaClass) {
		TableMeta<?> tableMeta = tableMetaMap.get(metaClass);
		if (tableMeta == null) {
			tableMeta = TableMetaHelper.createTableMeta(metaClass);
			tableMetaMap.put(metaClass, tableMeta);
		}
	}

	public TableMeta<?> getTableMeta(Class<?> metaClass) {
		return tableMetaMap.get(metaClass);
	}

	private Object getObjectFieldRawValue(TableMeta<?> tableMeta, Object o, String field) {
		Object v = BaseUtils.getPropertyAutoCreate(o, field);
		ColumnConvert convert = tableMeta.getConvertByField(field);
		return (convert != null) ? convert.marshal(v) : v;
	}

	private void setObjectFieldByRawValue(TableMeta<?> tableMeta, Object o, String field, Object rv) {
		ColumnConvert convert = tableMeta.getConvertByField(field);
		BaseUtils.setPropertyAutoCreate(o, field, ((convert != null) ? convert.unmarshal(rv) : rv));
	}

	private Object getRawValueByColumn(TableMeta<?> tableMeta, String column, Object v) {
		ColumnConvert convert = tableMeta.getConvertByColumn(column);
		return (convert != null) ? convert.marshal(v) : v;
	}

	private Object[] getRawValuesByColumns(TableMeta<?> tableMeta, String[] columns, Object[] vs) {
		if (vs == null) {
			return null;
		}
		Assert.isTrue(vs.length % columns.length == 0);

		Object[] rvs = new Object[vs.length];
		for (int i = 0; i < rvs.length; ++i) {
			rvs[i] = getRawValueByColumn(tableMeta, columns[i % columns.length], vs[i]);
		}
		return rvs;
	}

	private Object[] getPrimaryRawValues(TableMeta<?> tableMeta, Object... ids) {
		return getRawValuesByColumns(tableMeta, tableMeta.getPrimaryColumns(), ids);
	}

	public void addObject(Class<?> clazz, Object o) {
		TableMeta<?> tableMeta = getTableMeta(clazz);
		Assert.notNull(tableMeta);
		Assert.notNull(o);
		if (tableMeta.isAutoKey()) {
			String[] fields = tableMeta.getNoPrimaryFields();
			Object[] params = new Object[fields.length];
			for (int i = 0; i < fields.length; ++i) {
				params[i] = getObjectFieldRawValue(tableMeta, o, fields[i]);
			}
			Number r = getCommonJdbcTemplate().insertObject(tableMeta.getTableName(), tableMeta.getNoPrimaryColumns(),
					params);
			if (r == null) {
				throw new CommonJdbcDataAccessException("generate auto key exception");
			}
			setObjectFieldByRawValue(tableMeta, o, tableMeta.getPrimaryFields()[0], r);
		} else {
			String[] fields = tableMeta.getFields();
			Object[] params = new Object[fields.length];
			for (int i = 0; i < fields.length; ++i) {
				params[i] = getObjectFieldRawValue(tableMeta, o, fields[i]);
			}
			getCommonJdbcTemplate().addObject(tableMeta.getTableName(), tableMeta.getColumns(), params);
		}
	}

	public boolean deleteById(Class<?> clazz, Object... ids) {
		TableMeta<?> tableMeta = getTableMeta(clazz);
		Assert.notNull(tableMeta);
		Assert.isTrue(tableMeta.getPrimaryColumns().length == ids.length,
				"argument length is not equals with primary key length");

		Object[] innerIds = getPrimaryRawValues(tableMeta, ids);

		String sql = DBUtils.prepareDeleteSql(tableMeta.getTableName(), tableMeta.getPrimaryColumns());
		return getCommonJdbcTemplate().update(sql, innerIds) == 1;
	}
}
