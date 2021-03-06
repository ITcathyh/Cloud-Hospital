/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package top.itcat.rpc.service.model;

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
public class RegistrationLevel implements org.apache.thrift.TBase<RegistrationLevel, RegistrationLevel._Fields>, java.io.Serializable, Cloneable, Comparable<RegistrationLevel> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RegistrationLevel");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField IS_DEFAULT_FIELD_DESC = new org.apache.thrift.protocol.TField("is_default", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField DISPLAY_SEQ_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("display_seq_num", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("price", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField VALID_FIELD_DESC = new org.apache.thrift.protocol.TField("valid", org.apache.thrift.protocol.TType.I32, (short)255);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RegistrationLevelStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RegistrationLevelTupleSchemeFactory());
  }

  public long id; // optional
  public String code; // optional
  public String name; // optional
  public boolean is_default; // optional
  public long display_seq_num; // optional
  public double price; // optional
  public int valid; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    CODE((short)2, "code"),
    NAME((short)3, "name"),
    IS_DEFAULT((short)4, "is_default"),
    DISPLAY_SEQ_NUM((short)5, "display_seq_num"),
    PRICE((short)6, "price"),
    VALID((short)255, "valid");

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
        case 1: // ID
          return ID;
        case 2: // CODE
          return CODE;
        case 3: // NAME
          return NAME;
        case 4: // IS_DEFAULT
          return IS_DEFAULT;
        case 5: // DISPLAY_SEQ_NUM
          return DISPLAY_SEQ_NUM;
        case 6: // PRICE
          return PRICE;
        case 255: // VALID
          return VALID;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __IS_DEFAULT_ISSET_ID = 1;
  private static final int __DISPLAY_SEQ_NUM_ISSET_ID = 2;
  private static final int __PRICE_ISSET_ID = 3;
  private static final int __VALID_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.CODE,_Fields.NAME,_Fields.IS_DEFAULT,_Fields.DISPLAY_SEQ_NUM,_Fields.PRICE,_Fields.VALID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_DEFAULT, new org.apache.thrift.meta_data.FieldMetaData("is_default", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.DISPLAY_SEQ_NUM, new org.apache.thrift.meta_data.FieldMetaData("display_seq_num", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.PRICE, new org.apache.thrift.meta_data.FieldMetaData("price", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.VALID, new org.apache.thrift.meta_data.FieldMetaData("valid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RegistrationLevel.class, metaDataMap);
  }

  public RegistrationLevel() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RegistrationLevel(RegistrationLevel other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetCode()) {
      this.code = other.code;
    }
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.is_default = other.is_default;
    this.display_seq_num = other.display_seq_num;
    this.price = other.price;
    this.valid = other.valid;
  }

  public RegistrationLevel deepCopy() {
    return new RegistrationLevel(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.code = null;
    this.name = null;
    setIs_defaultIsSet(false);
    this.is_default = false;
    setDisplay_seq_numIsSet(false);
    this.display_seq_num = 0;
    setPriceIsSet(false);
    this.price = 0.0;
    setValidIsSet(false);
    this.valid = 0;
  }

  public long getId() {
    return this.id;
  }

  public RegistrationLevel setId(long id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public String getCode() {
    return this.code;
  }

  public RegistrationLevel setCode(String code) {
    this.code = code;
    return this;
  }

  public void unsetCode() {
    this.code = null;
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return this.code != null;
  }

  public void setCodeIsSet(boolean value) {
    if (!value) {
      this.code = null;
    }
  }

  public String getName() {
    return this.name;
  }

  public RegistrationLevel setName(String name) {
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

  public boolean isIs_default() {
    return this.is_default;
  }

  public RegistrationLevel setIs_default(boolean is_default) {
    this.is_default = is_default;
    setIs_defaultIsSet(true);
    return this;
  }

  public void unsetIs_default() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IS_DEFAULT_ISSET_ID);
  }

  /** Returns true if field is_default is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_default() {
    return EncodingUtils.testBit(__isset_bitfield, __IS_DEFAULT_ISSET_ID);
  }

  public void setIs_defaultIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IS_DEFAULT_ISSET_ID, value);
  }

  public long getDisplay_seq_num() {
    return this.display_seq_num;
  }

  public RegistrationLevel setDisplay_seq_num(long display_seq_num) {
    this.display_seq_num = display_seq_num;
    setDisplay_seq_numIsSet(true);
    return this;
  }

  public void unsetDisplay_seq_num() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DISPLAY_SEQ_NUM_ISSET_ID);
  }

  /** Returns true if field display_seq_num is set (has been assigned a value) and false otherwise */
  public boolean isSetDisplay_seq_num() {
    return EncodingUtils.testBit(__isset_bitfield, __DISPLAY_SEQ_NUM_ISSET_ID);
  }

  public void setDisplay_seq_numIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DISPLAY_SEQ_NUM_ISSET_ID, value);
  }

  public double getPrice() {
    return this.price;
  }

  public RegistrationLevel setPrice(double price) {
    this.price = price;
    setPriceIsSet(true);
    return this;
  }

  public void unsetPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  /** Returns true if field price is set (has been assigned a value) and false otherwise */
  public boolean isSetPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  public void setPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRICE_ISSET_ID, value);
  }

  public int getValid() {
    return this.valid;
  }

  public RegistrationLevel setValid(int valid) {
    this.valid = valid;
    setValidIsSet(true);
    return this;
  }

  public void unsetValid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VALID_ISSET_ID);
  }

  /** Returns true if field valid is set (has been assigned a value) and false otherwise */
  public boolean isSetValid() {
    return EncodingUtils.testBit(__isset_bitfield, __VALID_ISSET_ID);
  }

  public void setValidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VALID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
      }
      break;

    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((String)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case IS_DEFAULT:
      if (value == null) {
        unsetIs_default();
      } else {
        setIs_default((Boolean)value);
      }
      break;

    case DISPLAY_SEQ_NUM:
      if (value == null) {
        unsetDisplay_seq_num();
      } else {
        setDisplay_seq_num((Long)value);
      }
      break;

    case PRICE:
      if (value == null) {
        unsetPrice();
      } else {
        setPrice((Double)value);
      }
      break;

    case VALID:
      if (value == null) {
        unsetValid();
      } else {
        setValid((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getId());

    case CODE:
      return getCode();

    case NAME:
      return getName();

    case IS_DEFAULT:
      return Boolean.valueOf(isIs_default());

    case DISPLAY_SEQ_NUM:
      return Long.valueOf(getDisplay_seq_num());

    case PRICE:
      return Double.valueOf(getPrice());

    case VALID:
      return Integer.valueOf(getValid());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case CODE:
      return isSetCode();
    case NAME:
      return isSetName();
    case IS_DEFAULT:
      return isSetIs_default();
    case DISPLAY_SEQ_NUM:
      return isSetDisplay_seq_num();
    case PRICE:
      return isSetPrice();
    case VALID:
      return isSetValid();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RegistrationLevel)
      return this.equals((RegistrationLevel)that);
    return false;
  }

  public boolean equals(RegistrationLevel that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_code = true && this.isSetCode();
    boolean that_present_code = true && that.isSetCode();
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (!this.code.equals(that.code))
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

    boolean this_present_is_default = true && this.isSetIs_default();
    boolean that_present_is_default = true && that.isSetIs_default();
    if (this_present_is_default || that_present_is_default) {
      if (!(this_present_is_default && that_present_is_default))
        return false;
      if (this.is_default != that.is_default)
        return false;
    }

    boolean this_present_display_seq_num = true && this.isSetDisplay_seq_num();
    boolean that_present_display_seq_num = true && that.isSetDisplay_seq_num();
    if (this_present_display_seq_num || that_present_display_seq_num) {
      if (!(this_present_display_seq_num && that_present_display_seq_num))
        return false;
      if (this.display_seq_num != that.display_seq_num)
        return false;
    }

    boolean this_present_price = true && this.isSetPrice();
    boolean that_present_price = true && that.isSetPrice();
    if (this_present_price || that_present_price) {
      if (!(this_present_price && that_present_price))
        return false;
      if (this.price != that.price)
        return false;
    }

    boolean this_present_valid = true && this.isSetValid();
    boolean that_present_valid = true && that.isSetValid();
    if (this_present_valid || that_present_valid) {
      if (!(this_present_valid && that_present_valid))
        return false;
      if (this.valid != that.valid)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_code = true && (isSetCode());
    list.add(present_code);
    if (present_code)
      list.add(code);

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_is_default = true && (isSetIs_default());
    list.add(present_is_default);
    if (present_is_default)
      list.add(is_default);

    boolean present_display_seq_num = true && (isSetDisplay_seq_num());
    list.add(present_display_seq_num);
    if (present_display_seq_num)
      list.add(display_seq_num);

    boolean present_price = true && (isSetPrice());
    list.add(present_price);
    if (present_price)
      list.add(price);

    boolean present_valid = true && (isSetValid());
    list.add(present_valid);
    if (present_valid)
      list.add(valid);

    return list.hashCode();
  }

  @Override
  public int compareTo(RegistrationLevel other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
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
    lastComparison = Boolean.valueOf(isSetIs_default()).compareTo(other.isSetIs_default());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_default()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_default, other.is_default);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDisplay_seq_num()).compareTo(other.isSetDisplay_seq_num());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDisplay_seq_num()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.display_seq_num, other.display_seq_num);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrice()).compareTo(other.isSetPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.price, other.price);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetValid()).compareTo(other.isSetValid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.valid, other.valid);
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
    StringBuilder sb = new StringBuilder("RegistrationLevel(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetCode()) {
      if (!first) sb.append(", ");
      sb.append("code:");
      if (this.code == null) {
        sb.append("null");
      } else {
        sb.append(this.code);
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
    if (isSetIs_default()) {
      if (!first) sb.append(", ");
      sb.append("is_default:");
      sb.append(this.is_default);
      first = false;
    }
    if (isSetDisplay_seq_num()) {
      if (!first) sb.append(", ");
      sb.append("display_seq_num:");
      sb.append(this.display_seq_num);
      first = false;
    }
    if (isSetPrice()) {
      if (!first) sb.append(", ");
      sb.append("price:");
      sb.append(this.price);
      first = false;
    }
    if (isSetValid()) {
      if (!first) sb.append(", ");
      sb.append("valid:");
      sb.append(this.valid);
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

  private static class RegistrationLevelStandardSchemeFactory implements SchemeFactory {
    public RegistrationLevelStandardScheme getScheme() {
      return new RegistrationLevelStandardScheme();
    }
  }

  private static class RegistrationLevelStandardScheme extends StandardScheme<RegistrationLevel> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RegistrationLevel struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.id = iprot.readI64();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.code = iprot.readString();
              struct.setCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // IS_DEFAULT
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_default = iprot.readBool();
              struct.setIs_defaultIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DISPLAY_SEQ_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.display_seq_num = iprot.readI64();
              struct.setDisplay_seq_numIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.price = iprot.readDouble();
              struct.setPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 255: // VALID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.valid = iprot.readI32();
              struct.setValidIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RegistrationLevel struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI64(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.code != null) {
        if (struct.isSetCode()) {
          oprot.writeFieldBegin(CODE_FIELD_DESC);
          oprot.writeString(struct.code);
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
      if (struct.isSetIs_default()) {
        oprot.writeFieldBegin(IS_DEFAULT_FIELD_DESC);
        oprot.writeBool(struct.is_default);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDisplay_seq_num()) {
        oprot.writeFieldBegin(DISPLAY_SEQ_NUM_FIELD_DESC);
        oprot.writeI64(struct.display_seq_num);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPrice()) {
        oprot.writeFieldBegin(PRICE_FIELD_DESC);
        oprot.writeDouble(struct.price);
        oprot.writeFieldEnd();
      }
      if (struct.isSetValid()) {
        oprot.writeFieldBegin(VALID_FIELD_DESC);
        oprot.writeI32(struct.valid);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RegistrationLevelTupleSchemeFactory implements SchemeFactory {
    public RegistrationLevelTupleScheme getScheme() {
      return new RegistrationLevelTupleScheme();
    }
  }

  private static class RegistrationLevelTupleScheme extends TupleScheme<RegistrationLevel> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RegistrationLevel struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetCode()) {
        optionals.set(1);
      }
      if (struct.isSetName()) {
        optionals.set(2);
      }
      if (struct.isSetIs_default()) {
        optionals.set(3);
      }
      if (struct.isSetDisplay_seq_num()) {
        optionals.set(4);
      }
      if (struct.isSetPrice()) {
        optionals.set(5);
      }
      if (struct.isSetValid()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetCode()) {
        oprot.writeString(struct.code);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetIs_default()) {
        oprot.writeBool(struct.is_default);
      }
      if (struct.isSetDisplay_seq_num()) {
        oprot.writeI64(struct.display_seq_num);
      }
      if (struct.isSetPrice()) {
        oprot.writeDouble(struct.price);
      }
      if (struct.isSetValid()) {
        oprot.writeI32(struct.valid);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RegistrationLevel struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readI64();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.code = iprot.readString();
        struct.setCodeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.is_default = iprot.readBool();
        struct.setIs_defaultIsSet(true);
      }
      if (incoming.get(4)) {
        struct.display_seq_num = iprot.readI64();
        struct.setDisplay_seq_numIsSet(true);
      }
      if (incoming.get(5)) {
        struct.price = iprot.readDouble();
        struct.setPriceIsSet(true);
      }
      if (incoming.get(6)) {
        struct.valid = iprot.readI32();
        struct.setValidIsSet(true);
      }
    }
  }

}

