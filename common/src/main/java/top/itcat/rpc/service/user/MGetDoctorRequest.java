/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package top.itcat.rpc.service.user;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2019-7-3")
public class MGetDoctorRequest implements org.apache.thrift.TBase<MGetDoctorRequest, MGetDoctorRequest._Fields>, java.io.Serializable, Cloneable, Comparable<MGetDoctorRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MGetDoctorRequest");

  private static final org.apache.thrift.protocol.TField DEPART_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("departId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("title", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField UIDS_FIELD_DESC = new org.apache.thrift.protocol.TField("uids", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ID_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("idNum", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField OFFSET_FIELD_DESC = new org.apache.thrift.protocol.TField("offset", org.apache.thrift.protocol.TType.I32, (short)100);
  private static final org.apache.thrift.protocol.TField LIMIT_FIELD_DESC = new org.apache.thrift.protocol.TField("limit", org.apache.thrift.protocol.TType.I32, (short)101);
  private static final org.apache.thrift.protocol.TField SEARCH_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("searchKey", org.apache.thrift.protocol.TType.STRING, (short)102);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MGetDoctorRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MGetDoctorRequestTupleSchemeFactory());
  }

  public long departId; // optional
  /**
   * 
   * @see top.itcat.rpc.service.model.DoctorTitleEnum
   */
  public top.itcat.rpc.service.model.DoctorTitleEnum title; // optional
  public List<Long> uids; // optional
  public String name; // optional
  public String idNum; // optional
  public int offset; // optional
  public int limit; // optional
  public String searchKey; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DEPART_ID((short)1, "departId"),
    /**
     * 
     * @see top.itcat.rpc.service.model.DoctorTitleEnum
     */
    TITLE((short)2, "title"),
    UIDS((short)3, "uids"),
    NAME((short)4, "name"),
    ID_NUM((short)5, "idNum"),
    OFFSET((short)100, "offset"),
    LIMIT((short)101, "limit"),
    SEARCH_KEY((short)102, "searchKey");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DEPART_ID
          return DEPART_ID;
        case 2: // TITLE
          return TITLE;
        case 3: // UIDS
          return UIDS;
        case 4: // NAME
          return NAME;
        case 5: // ID_NUM
          return ID_NUM;
        case 100: // OFFSET
          return OFFSET;
        case 101: // LIMIT
          return LIMIT;
        case 102: // SEARCH_KEY
          return SEARCH_KEY;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __DEPARTID_ISSET_ID = 0;
  private static final int __OFFSET_ISSET_ID = 1;
  private static final int __LIMIT_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.DEPART_ID,_Fields.TITLE,_Fields.UIDS,_Fields.NAME,_Fields.ID_NUM,_Fields.OFFSET,_Fields.LIMIT,_Fields.SEARCH_KEY};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DEPART_ID, new org.apache.thrift.meta_data.FieldMetaData("departId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TITLE, new org.apache.thrift.meta_data.FieldMetaData("title", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, top.itcat.rpc.service.model.DoctorTitleEnum.class)));
    tmpMap.put(_Fields.UIDS, new org.apache.thrift.meta_data.FieldMetaData("uids", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ID_NUM, new org.apache.thrift.meta_data.FieldMetaData("idNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OFFSET, new org.apache.thrift.meta_data.FieldMetaData("offset", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LIMIT, new org.apache.thrift.meta_data.FieldMetaData("limit", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SEARCH_KEY, new org.apache.thrift.meta_data.FieldMetaData("searchKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MGetDoctorRequest.class, metaDataMap);
  }

  public MGetDoctorRequest() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MGetDoctorRequest(MGetDoctorRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    this.departId = other.departId;
    if (other.isSetTitle()) {
      this.title = other.title;
    }
    if (other.isSetUids()) {
      List<Long> __this__uids = new ArrayList<Long>(other.uids);
      this.uids = __this__uids;
    }
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetIdNum()) {
      this.idNum = other.idNum;
    }
    this.offset = other.offset;
    this.limit = other.limit;
    if (other.isSetSearchKey()) {
      this.searchKey = other.searchKey;
    }
  }

  public MGetDoctorRequest deepCopy() {
    return new MGetDoctorRequest(this);
  }

  @Override
  public void clear() {
    setDepartIdIsSet(false);
    this.departId = 0;
    this.title = null;
    this.uids = null;
    this.name = null;
    this.idNum = null;
    setOffsetIsSet(false);
    this.offset = 0;
    setLimitIsSet(false);
    this.limit = 0;
    this.searchKey = null;
  }

  public long getDepartId() {
    return this.departId;
  }

  public MGetDoctorRequest setDepartId(long departId) {
    this.departId = departId;
    setDepartIdIsSet(true);
    return this;
  }

  public void unsetDepartId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DEPARTID_ISSET_ID);
  }

  /** Returns true if field departId is set (has been assigned a value) and false otherwise */
  public boolean isSetDepartId() {
    return EncodingUtils.testBit(__isset_bitfield, __DEPARTID_ISSET_ID);
  }

  public void setDepartIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DEPARTID_ISSET_ID, value);
  }

  /**
   * 
   * @see top.itcat.rpc.service.model.DoctorTitleEnum
   */
  public top.itcat.rpc.service.model.DoctorTitleEnum getTitle() {
    return this.title;
  }

  /**
   * 
   * @see top.itcat.rpc.service.model.DoctorTitleEnum
   */
  public MGetDoctorRequest setTitle(top.itcat.rpc.service.model.DoctorTitleEnum title) {
    this.title = title;
    return this;
  }

  public void unsetTitle() {
    this.title = null;
  }

  /** Returns true if field title is set (has been assigned a value) and false otherwise */
  public boolean isSetTitle() {
    return this.title != null;
  }

  public void setTitleIsSet(boolean value) {
    if (!value) {
      this.title = null;
    }
  }

  public int getUidsSize() {
    return (this.uids == null) ? 0 : this.uids.size();
  }

  public java.util.Iterator<Long> getUidsIterator() {
    return (this.uids == null) ? null : this.uids.iterator();
  }

  public void addToUids(long elem) {
    if (this.uids == null) {
      this.uids = new ArrayList<Long>();
    }
    this.uids.add(elem);
  }

  public List<Long> getUids() {
    return this.uids;
  }

  public MGetDoctorRequest setUids(List<Long> uids) {
    this.uids = uids;
    return this;
  }

  public void unsetUids() {
    this.uids = null;
  }

  /** Returns true if field uids is set (has been assigned a value) and false otherwise */
  public boolean isSetUids() {
    return this.uids != null;
  }

  public void setUidsIsSet(boolean value) {
    if (!value) {
      this.uids = null;
    }
  }

  public String getName() {
    return this.name;
  }

  public MGetDoctorRequest setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getIdNum() {
    return this.idNum;
  }

  public MGetDoctorRequest setIdNum(String idNum) {
    this.idNum = idNum;
    return this;
  }

  public void unsetIdNum() {
    this.idNum = null;
  }

  /** Returns true if field idNum is set (has been assigned a value) and false otherwise */
  public boolean isSetIdNum() {
    return this.idNum != null;
  }

  public void setIdNumIsSet(boolean value) {
    if (!value) {
      this.idNum = null;
    }
  }

  public int getOffset() {
    return this.offset;
  }

  public MGetDoctorRequest setOffset(int offset) {
    this.offset = offset;
    setOffsetIsSet(true);
    return this;
  }

  public void unsetOffset() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OFFSET_ISSET_ID);
  }

  /** Returns true if field offset is set (has been assigned a value) and false otherwise */
  public boolean isSetOffset() {
    return EncodingUtils.testBit(__isset_bitfield, __OFFSET_ISSET_ID);
  }

  public void setOffsetIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OFFSET_ISSET_ID, value);
  }

  public int getLimit() {
    return this.limit;
  }

  public MGetDoctorRequest setLimit(int limit) {
    this.limit = limit;
    setLimitIsSet(true);
    return this;
  }

  public void unsetLimit() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LIMIT_ISSET_ID);
  }

  /** Returns true if field limit is set (has been assigned a value) and false otherwise */
  public boolean isSetLimit() {
    return EncodingUtils.testBit(__isset_bitfield, __LIMIT_ISSET_ID);
  }

  public void setLimitIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LIMIT_ISSET_ID, value);
  }

  public String getSearchKey() {
    return this.searchKey;
  }

  public MGetDoctorRequest setSearchKey(String searchKey) {
    this.searchKey = searchKey;
    return this;
  }

  public void unsetSearchKey() {
    this.searchKey = null;
  }

  /** Returns true if field searchKey is set (has been assigned a value) and false otherwise */
  public boolean isSetSearchKey() {
    return this.searchKey != null;
  }

  public void setSearchKeyIsSet(boolean value) {
    if (!value) {
      this.searchKey = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DEPART_ID:
      if (value == null) {
        unsetDepartId();
      } else {
        setDepartId((Long)value);
      }
      break;

    case TITLE:
      if (value == null) {
        unsetTitle();
      } else {
        setTitle((top.itcat.rpc.service.model.DoctorTitleEnum)value);
      }
      break;

    case UIDS:
      if (value == null) {
        unsetUids();
      } else {
        setUids((List<Long>)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case ID_NUM:
      if (value == null) {
        unsetIdNum();
      } else {
        setIdNum((String)value);
      }
      break;

    case OFFSET:
      if (value == null) {
        unsetOffset();
      } else {
        setOffset((Integer)value);
      }
      break;

    case LIMIT:
      if (value == null) {
        unsetLimit();
      } else {
        setLimit((Integer)value);
      }
      break;

    case SEARCH_KEY:
      if (value == null) {
        unsetSearchKey();
      } else {
        setSearchKey((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DEPART_ID:
      return Long.valueOf(getDepartId());

    case TITLE:
      return getTitle();

    case UIDS:
      return getUids();

    case NAME:
      return getName();

    case ID_NUM:
      return getIdNum();

    case OFFSET:
      return Integer.valueOf(getOffset());

    case LIMIT:
      return Integer.valueOf(getLimit());

    case SEARCH_KEY:
      return getSearchKey();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DEPART_ID:
      return isSetDepartId();
    case TITLE:
      return isSetTitle();
    case UIDS:
      return isSetUids();
    case NAME:
      return isSetName();
    case ID_NUM:
      return isSetIdNum();
    case OFFSET:
      return isSetOffset();
    case LIMIT:
      return isSetLimit();
    case SEARCH_KEY:
      return isSetSearchKey();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MGetDoctorRequest)
      return this.equals((MGetDoctorRequest)that);
    return false;
  }

  public boolean equals(MGetDoctorRequest that) {
    if (that == null)
      return false;

    boolean this_present_departId = true && this.isSetDepartId();
    boolean that_present_departId = true && that.isSetDepartId();
    if (this_present_departId || that_present_departId) {
      if (!(this_present_departId && that_present_departId))
        return false;
      if (this.departId != that.departId)
        return false;
    }

    boolean this_present_title = true && this.isSetTitle();
    boolean that_present_title = true && that.isSetTitle();
    if (this_present_title || that_present_title) {
      if (!(this_present_title && that_present_title))
        return false;
      if (!this.title.equals(that.title))
        return false;
    }

    boolean this_present_uids = true && this.isSetUids();
    boolean that_present_uids = true && that.isSetUids();
    if (this_present_uids || that_present_uids) {
      if (!(this_present_uids && that_present_uids))
        return false;
      if (!this.uids.equals(that.uids))
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_idNum = true && this.isSetIdNum();
    boolean that_present_idNum = true && that.isSetIdNum();
    if (this_present_idNum || that_present_idNum) {
      if (!(this_present_idNum && that_present_idNum))
        return false;
      if (!this.idNum.equals(that.idNum))
        return false;
    }

    boolean this_present_offset = true && this.isSetOffset();
    boolean that_present_offset = true && that.isSetOffset();
    if (this_present_offset || that_present_offset) {
      if (!(this_present_offset && that_present_offset))
        return false;
      if (this.offset != that.offset)
        return false;
    }

    boolean this_present_limit = true && this.isSetLimit();
    boolean that_present_limit = true && that.isSetLimit();
    if (this_present_limit || that_present_limit) {
      if (!(this_present_limit && that_present_limit))
        return false;
      if (this.limit != that.limit)
        return false;
    }

    boolean this_present_searchKey = true && this.isSetSearchKey();
    boolean that_present_searchKey = true && that.isSetSearchKey();
    if (this_present_searchKey || that_present_searchKey) {
      if (!(this_present_searchKey && that_present_searchKey))
        return false;
      if (!this.searchKey.equals(that.searchKey))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_departId = true && (isSetDepartId());
    list.add(present_departId);
    if (present_departId)
      list.add(departId);

    boolean present_title = true && (isSetTitle());
    list.add(present_title);
    if (present_title)
      list.add(title.getValue());

    boolean present_uids = true && (isSetUids());
    list.add(present_uids);
    if (present_uids)
      list.add(uids);

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_idNum = true && (isSetIdNum());
    list.add(present_idNum);
    if (present_idNum)
      list.add(idNum);

    boolean present_offset = true && (isSetOffset());
    list.add(present_offset);
    if (present_offset)
      list.add(offset);

    boolean present_limit = true && (isSetLimit());
    list.add(present_limit);
    if (present_limit)
      list.add(limit);

    boolean present_searchKey = true && (isSetSearchKey());
    list.add(present_searchKey);
    if (present_searchKey)
      list.add(searchKey);

    return list.hashCode();
  }

  @Override
  public int compareTo(MGetDoctorRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDepartId()).compareTo(other.isSetDepartId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDepartId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.departId, other.departId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTitle()).compareTo(other.isSetTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.title, other.title);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUids()).compareTo(other.isSetUids());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUids()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uids, other.uids);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIdNum()).compareTo(other.isSetIdNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.idNum, other.idNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOffset()).compareTo(other.isSetOffset());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOffset()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.offset, other.offset);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLimit()).compareTo(other.isSetLimit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLimit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.limit, other.limit);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSearchKey()).compareTo(other.isSetSearchKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSearchKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.searchKey, other.searchKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("MGetDoctorRequest(");
    boolean first = true;

    if (isSetDepartId()) {
      sb.append("departId:");
      sb.append(this.departId);
      first = false;
    }
    if (isSetTitle()) {
      if (!first) sb.append(", ");
      sb.append("title:");
      if (this.title == null) {
        sb.append("null");
      } else {
        sb.append(this.title);
      }
      first = false;
    }
    if (isSetUids()) {
      if (!first) sb.append(", ");
      sb.append("uids:");
      if (this.uids == null) {
        sb.append("null");
      } else {
        sb.append(this.uids);
      }
      first = false;
    }
    if (isSetName()) {
      if (!first) sb.append(", ");
      sb.append("name:");
      if (this.name == null) {
        sb.append("null");
      } else {
        sb.append(this.name);
      }
      first = false;
    }
    if (isSetIdNum()) {
      if (!first) sb.append(", ");
      sb.append("idNum:");
      if (this.idNum == null) {
        sb.append("null");
      } else {
        sb.append(this.idNum);
      }
      first = false;
    }
    if (isSetOffset()) {
      if (!first) sb.append(", ");
      sb.append("offset:");
      sb.append(this.offset);
      first = false;
    }
    if (isSetLimit()) {
      if (!first) sb.append(", ");
      sb.append("limit:");
      sb.append(this.limit);
      first = false;
    }
    if (isSetSearchKey()) {
      if (!first) sb.append(", ");
      sb.append("searchKey:");
      if (this.searchKey == null) {
        sb.append("null");
      } else {
        sb.append(this.searchKey);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MGetDoctorRequestStandardSchemeFactory implements SchemeFactory {
    public MGetDoctorRequestStandardScheme getScheme() {
      return new MGetDoctorRequestStandardScheme();
    }
  }

  private static class MGetDoctorRequestStandardScheme extends StandardScheme<MGetDoctorRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MGetDoctorRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DEPART_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.departId = iprot.readI64();
              struct.setDepartIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.title = top.itcat.rpc.service.model.DoctorTitleEnum.findByValue(iprot.readI32());
              struct.setTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // UIDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.uids = new ArrayList<Long>(_list0.size);
                long _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readI64();
                  struct.uids.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setUidsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ID_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.idNum = iprot.readString();
              struct.setIdNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 100: // OFFSET
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.offset = iprot.readI32();
              struct.setOffsetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 101: // LIMIT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.limit = iprot.readI32();
              struct.setLimitIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 102: // SEARCH_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.searchKey = iprot.readString();
              struct.setSearchKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, MGetDoctorRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetDepartId()) {
        oprot.writeFieldBegin(DEPART_ID_FIELD_DESC);
        oprot.writeI64(struct.departId);
        oprot.writeFieldEnd();
      }
      if (struct.title != null) {
        if (struct.isSetTitle()) {
          oprot.writeFieldBegin(TITLE_FIELD_DESC);
          oprot.writeI32(struct.title.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.uids != null) {
        if (struct.isSetUids()) {
          oprot.writeFieldBegin(UIDS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.uids.size()));
            for (long _iter3 : struct.uids)
            {
              oprot.writeI64(_iter3);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.name != null) {
        if (struct.isSetName()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.idNum != null) {
        if (struct.isSetIdNum()) {
          oprot.writeFieldBegin(ID_NUM_FIELD_DESC);
          oprot.writeString(struct.idNum);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetOffset()) {
        oprot.writeFieldBegin(OFFSET_FIELD_DESC);
        oprot.writeI32(struct.offset);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLimit()) {
        oprot.writeFieldBegin(LIMIT_FIELD_DESC);
        oprot.writeI32(struct.limit);
        oprot.writeFieldEnd();
      }
      if (struct.searchKey != null) {
        if (struct.isSetSearchKey()) {
          oprot.writeFieldBegin(SEARCH_KEY_FIELD_DESC);
          oprot.writeString(struct.searchKey);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MGetDoctorRequestTupleSchemeFactory implements SchemeFactory {
    public MGetDoctorRequestTupleScheme getScheme() {
      return new MGetDoctorRequestTupleScheme();
    }
  }

  private static class MGetDoctorRequestTupleScheme extends TupleScheme<MGetDoctorRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MGetDoctorRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetDepartId()) {
        optionals.set(0);
      }
      if (struct.isSetTitle()) {
        optionals.set(1);
      }
      if (struct.isSetUids()) {
        optionals.set(2);
      }
      if (struct.isSetName()) {
        optionals.set(3);
      }
      if (struct.isSetIdNum()) {
        optionals.set(4);
      }
      if (struct.isSetOffset()) {
        optionals.set(5);
      }
      if (struct.isSetLimit()) {
        optionals.set(6);
      }
      if (struct.isSetSearchKey()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetDepartId()) {
        oprot.writeI64(struct.departId);
      }
      if (struct.isSetTitle()) {
        oprot.writeI32(struct.title.getValue());
      }
      if (struct.isSetUids()) {
        {
          oprot.writeI32(struct.uids.size());
          for (long _iter4 : struct.uids)
          {
            oprot.writeI64(_iter4);
          }
        }
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetIdNum()) {
        oprot.writeString(struct.idNum);
      }
      if (struct.isSetOffset()) {
        oprot.writeI32(struct.offset);
      }
      if (struct.isSetLimit()) {
        oprot.writeI32(struct.limit);
      }
      if (struct.isSetSearchKey()) {
        oprot.writeString(struct.searchKey);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MGetDoctorRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.departId = iprot.readI64();
        struct.setDepartIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.title = top.itcat.rpc.service.model.DoctorTitleEnum.findByValue(iprot.readI32());
        struct.setTitleIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.uids = new ArrayList<Long>(_list5.size);
          long _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readI64();
            struct.uids.add(_elem6);
          }
        }
        struct.setUidsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.idNum = iprot.readString();
        struct.setIdNumIsSet(true);
      }
      if (incoming.get(5)) {
        struct.offset = iprot.readI32();
        struct.setOffsetIsSet(true);
      }
      if (incoming.get(6)) {
        struct.limit = iprot.readI32();
        struct.setLimitIsSet(true);
      }
      if (incoming.get(7)) {
        struct.searchKey = iprot.readString();
        struct.setSearchKeyIsSet(true);
      }
    }
  }

}
