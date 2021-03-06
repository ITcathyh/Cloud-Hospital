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
public class MGetPatientRequest implements org.apache.thrift.TBase<MGetPatientRequest, MGetPatientRequest._Fields>, java.io.Serializable, Cloneable, Comparable<MGetPatientRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MGetPatientRequest");

  private static final org.apache.thrift.protocol.TField ID_NUMS_FIELD_DESC = new org.apache.thrift.protocol.TField("idNums", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField OFFSET_FIELD_DESC = new org.apache.thrift.protocol.TField("offset", org.apache.thrift.protocol.TType.I32, (short)100);
  private static final org.apache.thrift.protocol.TField LIMIT_FIELD_DESC = new org.apache.thrift.protocol.TField("limit", org.apache.thrift.protocol.TType.I32, (short)101);
  private static final org.apache.thrift.protocol.TField SEARCH_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("searchKey", org.apache.thrift.protocol.TType.STRING, (short)102);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MGetPatientRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MGetPatientRequestTupleSchemeFactory());
  }

  public List<String> idNums; // optional
  public int offset; // optional
  public int limit; // optional
  public String searchKey; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID_NUMS((short)1, "idNums"),
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
        case 1: // ID_NUMS
          return ID_NUMS;
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
  private static final int __OFFSET_ISSET_ID = 0;
  private static final int __LIMIT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID_NUMS,_Fields.OFFSET,_Fields.LIMIT,_Fields.SEARCH_KEY};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID_NUMS, new org.apache.thrift.meta_data.FieldMetaData("idNums", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.OFFSET, new org.apache.thrift.meta_data.FieldMetaData("offset", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LIMIT, new org.apache.thrift.meta_data.FieldMetaData("limit", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SEARCH_KEY, new org.apache.thrift.meta_data.FieldMetaData("searchKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MGetPatientRequest.class, metaDataMap);
  }

  public MGetPatientRequest() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MGetPatientRequest(MGetPatientRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetIdNums()) {
      List<String> __this__idNums = new ArrayList<String>(other.idNums);
      this.idNums = __this__idNums;
    }
    this.offset = other.offset;
    this.limit = other.limit;
    if (other.isSetSearchKey()) {
      this.searchKey = other.searchKey;
    }
  }

  public MGetPatientRequest deepCopy() {
    return new MGetPatientRequest(this);
  }

  @Override
  public void clear() {
    this.idNums = null;
    setOffsetIsSet(false);
    this.offset = 0;
    setLimitIsSet(false);
    this.limit = 0;
    this.searchKey = null;
  }

  public int getIdNumsSize() {
    return (this.idNums == null) ? 0 : this.idNums.size();
  }

  public java.util.Iterator<String> getIdNumsIterator() {
    return (this.idNums == null) ? null : this.idNums.iterator();
  }

  public void addToIdNums(String elem) {
    if (this.idNums == null) {
      this.idNums = new ArrayList<String>();
    }
    this.idNums.add(elem);
  }

  public List<String> getIdNums() {
    return this.idNums;
  }

  public MGetPatientRequest setIdNums(List<String> idNums) {
    this.idNums = idNums;
    return this;
  }

  public void unsetIdNums() {
    this.idNums = null;
  }

  /** Returns true if field idNums is set (has been assigned a value) and false otherwise */
  public boolean isSetIdNums() {
    return this.idNums != null;
  }

  public void setIdNumsIsSet(boolean value) {
    if (!value) {
      this.idNums = null;
    }
  }

  public int getOffset() {
    return this.offset;
  }

  public MGetPatientRequest setOffset(int offset) {
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

  public MGetPatientRequest setLimit(int limit) {
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

  public MGetPatientRequest setSearchKey(String searchKey) {
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
    case ID_NUMS:
      if (value == null) {
        unsetIdNums();
      } else {
        setIdNums((List<String>)value);
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
    case ID_NUMS:
      return getIdNums();

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
    case ID_NUMS:
      return isSetIdNums();
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
    if (that instanceof MGetPatientRequest)
      return this.equals((MGetPatientRequest)that);
    return false;
  }

  public boolean equals(MGetPatientRequest that) {
    if (that == null)
      return false;

    boolean this_present_idNums = true && this.isSetIdNums();
    boolean that_present_idNums = true && that.isSetIdNums();
    if (this_present_idNums || that_present_idNums) {
      if (!(this_present_idNums && that_present_idNums))
        return false;
      if (!this.idNums.equals(that.idNums))
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

    boolean present_idNums = true && (isSetIdNums());
    list.add(present_idNums);
    if (present_idNums)
      list.add(idNums);

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
  public int compareTo(MGetPatientRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetIdNums()).compareTo(other.isSetIdNums());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdNums()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.idNums, other.idNums);
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
    StringBuilder sb = new StringBuilder("MGetPatientRequest(");
    boolean first = true;

    if (isSetIdNums()) {
      sb.append("idNums:");
      if (this.idNums == null) {
        sb.append("null");
      } else {
        sb.append(this.idNums);
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

  private static class MGetPatientRequestStandardSchemeFactory implements SchemeFactory {
    public MGetPatientRequestStandardScheme getScheme() {
      return new MGetPatientRequestStandardScheme();
    }
  }

  private static class MGetPatientRequestStandardScheme extends StandardScheme<MGetPatientRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MGetPatientRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID_NUMS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list160 = iprot.readListBegin();
                struct.idNums = new ArrayList<String>(_list160.size);
                String _elem161;
                for (int _i162 = 0; _i162 < _list160.size; ++_i162)
                {
                  _elem161 = iprot.readString();
                  struct.idNums.add(_elem161);
                }
                iprot.readListEnd();
              }
              struct.setIdNumsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MGetPatientRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.idNums != null) {
        if (struct.isSetIdNums()) {
          oprot.writeFieldBegin(ID_NUMS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.idNums.size()));
            for (String _iter163 : struct.idNums)
            {
              oprot.writeString(_iter163);
            }
            oprot.writeListEnd();
          }
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

  private static class MGetPatientRequestTupleSchemeFactory implements SchemeFactory {
    public MGetPatientRequestTupleScheme getScheme() {
      return new MGetPatientRequestTupleScheme();
    }
  }

  private static class MGetPatientRequestTupleScheme extends TupleScheme<MGetPatientRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MGetPatientRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetIdNums()) {
        optionals.set(0);
      }
      if (struct.isSetOffset()) {
        optionals.set(1);
      }
      if (struct.isSetLimit()) {
        optionals.set(2);
      }
      if (struct.isSetSearchKey()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetIdNums()) {
        {
          oprot.writeI32(struct.idNums.size());
          for (String _iter164 : struct.idNums)
          {
            oprot.writeString(_iter164);
          }
        }
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
    public void read(org.apache.thrift.protocol.TProtocol prot, MGetPatientRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list165 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.idNums = new ArrayList<String>(_list165.size);
          String _elem166;
          for (int _i167 = 0; _i167 < _list165.size; ++_i167)
          {
            _elem166 = iprot.readString();
            struct.idNums.add(_elem166);
          }
        }
        struct.setIdNumsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.offset = iprot.readI32();
        struct.setOffsetIsSet(true);
      }
      if (incoming.get(2)) {
        struct.limit = iprot.readI32();
        struct.setLimitIsSet(true);
      }
      if (incoming.get(3)) {
        struct.searchKey = iprot.readString();
        struct.setSearchKeyIsSet(true);
      }
    }
  }

}

