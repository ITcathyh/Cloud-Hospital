/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package top.itcat.rpc.service.diagnose;

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
public class GetRegistrationCountRequest implements org.apache.thrift.TBase<GetRegistrationCountRequest, GetRegistrationCountRequest._Fields>, java.io.Serializable, Cloneable, Comparable<GetRegistrationCountRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetRegistrationCountRequest");

  private static final org.apache.thrift.protocol.TField DOCTOR_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("doctorId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField DEPART_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("departId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField START_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("startTime", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField END_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("endTime", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetRegistrationCountRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetRegistrationCountRequestTupleSchemeFactory());
  }

  public long doctorId; // optional
  public long departId; // optional
  public long startTime; // required
  public long endTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DOCTOR_ID((short)1, "doctorId"),
    DEPART_ID((short)2, "departId"),
    START_TIME((short)3, "startTime"),
    END_TIME((short)4, "endTime");

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
        case 1: // DOCTOR_ID
          return DOCTOR_ID;
        case 2: // DEPART_ID
          return DEPART_ID;
        case 3: // START_TIME
          return START_TIME;
        case 4: // END_TIME
          return END_TIME;
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
  private static final int __DOCTORID_ISSET_ID = 0;
  private static final int __DEPARTID_ISSET_ID = 1;
  private static final int __STARTTIME_ISSET_ID = 2;
  private static final int __ENDTIME_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.DOCTOR_ID,_Fields.DEPART_ID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DOCTOR_ID, new org.apache.thrift.meta_data.FieldMetaData("doctorId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DEPART_ID, new org.apache.thrift.meta_data.FieldMetaData("departId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.START_TIME, new org.apache.thrift.meta_data.FieldMetaData("startTime", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.END_TIME, new org.apache.thrift.meta_data.FieldMetaData("endTime", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetRegistrationCountRequest.class, metaDataMap);
  }

  public GetRegistrationCountRequest() {
  }

  public GetRegistrationCountRequest(
    long startTime,
    long endTime)
  {
    this();
    this.startTime = startTime;
    setStartTimeIsSet(true);
    this.endTime = endTime;
    setEndTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetRegistrationCountRequest(GetRegistrationCountRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    this.doctorId = other.doctorId;
    this.departId = other.departId;
    this.startTime = other.startTime;
    this.endTime = other.endTime;
  }

  public GetRegistrationCountRequest deepCopy() {
    return new GetRegistrationCountRequest(this);
  }

  @Override
  public void clear() {
    setDoctorIdIsSet(false);
    this.doctorId = 0;
    setDepartIdIsSet(false);
    this.departId = 0;
    setStartTimeIsSet(false);
    this.startTime = 0;
    setEndTimeIsSet(false);
    this.endTime = 0;
  }

  public long getDoctorId() {
    return this.doctorId;
  }

  public GetRegistrationCountRequest setDoctorId(long doctorId) {
    this.doctorId = doctorId;
    setDoctorIdIsSet(true);
    return this;
  }

  public void unsetDoctorId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DOCTORID_ISSET_ID);
  }

  /** Returns true if field doctorId is set (has been assigned a value) and false otherwise */
  public boolean isSetDoctorId() {
    return EncodingUtils.testBit(__isset_bitfield, __DOCTORID_ISSET_ID);
  }

  public void setDoctorIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DOCTORID_ISSET_ID, value);
  }

  public long getDepartId() {
    return this.departId;
  }

  public GetRegistrationCountRequest setDepartId(long departId) {
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

  public long getStartTime() {
    return this.startTime;
  }

  public GetRegistrationCountRequest setStartTime(long startTime) {
    this.startTime = startTime;
    setStartTimeIsSet(true);
    return this;
  }

  public void unsetStartTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STARTTIME_ISSET_ID);
  }

  /** Returns true if field startTime is set (has been assigned a value) and false otherwise */
  public boolean isSetStartTime() {
    return EncodingUtils.testBit(__isset_bitfield, __STARTTIME_ISSET_ID);
  }

  public void setStartTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STARTTIME_ISSET_ID, value);
  }

  public long getEndTime() {
    return this.endTime;
  }

  public GetRegistrationCountRequest setEndTime(long endTime) {
    this.endTime = endTime;
    setEndTimeIsSet(true);
    return this;
  }

  public void unsetEndTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDTIME_ISSET_ID);
  }

  /** Returns true if field endTime is set (has been assigned a value) and false otherwise */
  public boolean isSetEndTime() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDTIME_ISSET_ID);
  }

  public void setEndTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDTIME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DOCTOR_ID:
      if (value == null) {
        unsetDoctorId();
      } else {
        setDoctorId((Long)value);
      }
      break;

    case DEPART_ID:
      if (value == null) {
        unsetDepartId();
      } else {
        setDepartId((Long)value);
      }
      break;

    case START_TIME:
      if (value == null) {
        unsetStartTime();
      } else {
        setStartTime((Long)value);
      }
      break;

    case END_TIME:
      if (value == null) {
        unsetEndTime();
      } else {
        setEndTime((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DOCTOR_ID:
      return Long.valueOf(getDoctorId());

    case DEPART_ID:
      return Long.valueOf(getDepartId());

    case START_TIME:
      return Long.valueOf(getStartTime());

    case END_TIME:
      return Long.valueOf(getEndTime());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DOCTOR_ID:
      return isSetDoctorId();
    case DEPART_ID:
      return isSetDepartId();
    case START_TIME:
      return isSetStartTime();
    case END_TIME:
      return isSetEndTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetRegistrationCountRequest)
      return this.equals((GetRegistrationCountRequest)that);
    return false;
  }

  public boolean equals(GetRegistrationCountRequest that) {
    if (that == null)
      return false;

    boolean this_present_doctorId = true && this.isSetDoctorId();
    boolean that_present_doctorId = true && that.isSetDoctorId();
    if (this_present_doctorId || that_present_doctorId) {
      if (!(this_present_doctorId && that_present_doctorId))
        return false;
      if (this.doctorId != that.doctorId)
        return false;
    }

    boolean this_present_departId = true && this.isSetDepartId();
    boolean that_present_departId = true && that.isSetDepartId();
    if (this_present_departId || that_present_departId) {
      if (!(this_present_departId && that_present_departId))
        return false;
      if (this.departId != that.departId)
        return false;
    }

    boolean this_present_startTime = true;
    boolean that_present_startTime = true;
    if (this_present_startTime || that_present_startTime) {
      if (!(this_present_startTime && that_present_startTime))
        return false;
      if (this.startTime != that.startTime)
        return false;
    }

    boolean this_present_endTime = true;
    boolean that_present_endTime = true;
    if (this_present_endTime || that_present_endTime) {
      if (!(this_present_endTime && that_present_endTime))
        return false;
      if (this.endTime != that.endTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_doctorId = true && (isSetDoctorId());
    list.add(present_doctorId);
    if (present_doctorId)
      list.add(doctorId);

    boolean present_departId = true && (isSetDepartId());
    list.add(present_departId);
    if (present_departId)
      list.add(departId);

    boolean present_startTime = true;
    list.add(present_startTime);
    if (present_startTime)
      list.add(startTime);

    boolean present_endTime = true;
    list.add(present_endTime);
    if (present_endTime)
      list.add(endTime);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetRegistrationCountRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDoctorId()).compareTo(other.isSetDoctorId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDoctorId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.doctorId, other.doctorId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetStartTime()).compareTo(other.isSetStartTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startTime, other.startTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndTime()).compareTo(other.isSetEndTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endTime, other.endTime);
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
    StringBuilder sb = new StringBuilder("GetRegistrationCountRequest(");
    boolean first = true;

    if (isSetDoctorId()) {
      sb.append("doctorId:");
      sb.append(this.doctorId);
      first = false;
    }
    if (isSetDepartId()) {
      if (!first) sb.append(", ");
      sb.append("departId:");
      sb.append(this.departId);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("startTime:");
    sb.append(this.startTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("endTime:");
    sb.append(this.endTime);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'startTime' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'endTime' because it's a primitive and you chose the non-beans generator.
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

  private static class GetRegistrationCountRequestStandardSchemeFactory implements SchemeFactory {
    public GetRegistrationCountRequestStandardScheme getScheme() {
      return new GetRegistrationCountRequestStandardScheme();
    }
  }

  private static class GetRegistrationCountRequestStandardScheme extends StandardScheme<GetRegistrationCountRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetRegistrationCountRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DOCTOR_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.doctorId = iprot.readI64();
              struct.setDoctorIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DEPART_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.departId = iprot.readI64();
              struct.setDepartIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // START_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.startTime = iprot.readI64();
              struct.setStartTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // END_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endTime = iprot.readI64();
              struct.setEndTimeIsSet(true);
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
      if (!struct.isSetStartTime()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'startTime' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetEndTime()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'endTime' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetRegistrationCountRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetDoctorId()) {
        oprot.writeFieldBegin(DOCTOR_ID_FIELD_DESC);
        oprot.writeI64(struct.doctorId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDepartId()) {
        oprot.writeFieldBegin(DEPART_ID_FIELD_DESC);
        oprot.writeI64(struct.departId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(START_TIME_FIELD_DESC);
      oprot.writeI64(struct.startTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(END_TIME_FIELD_DESC);
      oprot.writeI64(struct.endTime);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetRegistrationCountRequestTupleSchemeFactory implements SchemeFactory {
    public GetRegistrationCountRequestTupleScheme getScheme() {
      return new GetRegistrationCountRequestTupleScheme();
    }
  }

  private static class GetRegistrationCountRequestTupleScheme extends TupleScheme<GetRegistrationCountRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetRegistrationCountRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.startTime);
      oprot.writeI64(struct.endTime);
      BitSet optionals = new BitSet();
      if (struct.isSetDoctorId()) {
        optionals.set(0);
      }
      if (struct.isSetDepartId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetDoctorId()) {
        oprot.writeI64(struct.doctorId);
      }
      if (struct.isSetDepartId()) {
        oprot.writeI64(struct.departId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetRegistrationCountRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.startTime = iprot.readI64();
      struct.setStartTimeIsSet(true);
      struct.endTime = iprot.readI64();
      struct.setEndTimeIsSet(true);
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.doctorId = iprot.readI64();
        struct.setDoctorIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.departId = iprot.readI64();
        struct.setDepartIdIsSet(true);
      }
    }
  }

}

