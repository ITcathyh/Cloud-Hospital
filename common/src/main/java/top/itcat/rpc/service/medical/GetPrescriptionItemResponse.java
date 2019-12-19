/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package top.itcat.rpc.service.medical;

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
public class GetPrescriptionItemResponse implements org.apache.thrift.TBase<GetPrescriptionItemResponse, GetPrescriptionItemResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetPrescriptionItemResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetPrescriptionItemResponse");

  private static final org.apache.thrift.protocol.TField PRESCRIPTION_ITEMS_FIELD_DESC = new org.apache.thrift.protocol.TField("prescriptionItems", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField PRESCRIPTION_HERB_ITEMS_FIELD_DESC = new org.apache.thrift.protocol.TField("prescriptionHerbItems", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField TOTAL_FIELD_DESC = new org.apache.thrift.protocol.TField("total", org.apache.thrift.protocol.TType.I32, (short)254);
  private static final org.apache.thrift.protocol.TField BASE_RESP_FIELD_DESC = new org.apache.thrift.protocol.TField("BaseResp", org.apache.thrift.protocol.TType.STRUCT, (short)255);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetPrescriptionItemResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetPrescriptionItemResponseTupleSchemeFactory());
  }

  public List<top.itcat.rpc.service.model.PrescriptionItem> prescriptionItems; // optional
  public List<top.itcat.rpc.service.model.PrescriptionHerbItem> prescriptionHerbItems; // optional
  public int total; // required
  public top.itcat.rpc.base.BaseResp BaseResp; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PRESCRIPTION_ITEMS((short)1, "prescriptionItems"),
    PRESCRIPTION_HERB_ITEMS((short)2, "prescriptionHerbItems"),
    TOTAL((short)254, "total"),
    BASE_RESP((short)255, "BaseResp");

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
        case 1: // PRESCRIPTION_ITEMS
          return PRESCRIPTION_ITEMS;
        case 2: // PRESCRIPTION_HERB_ITEMS
          return PRESCRIPTION_HERB_ITEMS;
        case 254: // TOTAL
          return TOTAL;
        case 255: // BASE_RESP
          return BASE_RESP;
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
  private static final int __TOTAL_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PRESCRIPTION_ITEMS,_Fields.PRESCRIPTION_HERB_ITEMS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRESCRIPTION_ITEMS, new org.apache.thrift.meta_data.FieldMetaData("prescriptionItems", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, top.itcat.rpc.service.model.PrescriptionItem.class))));
    tmpMap.put(_Fields.PRESCRIPTION_HERB_ITEMS, new org.apache.thrift.meta_data.FieldMetaData("prescriptionHerbItems", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, top.itcat.rpc.service.model.PrescriptionHerbItem.class))));
    tmpMap.put(_Fields.TOTAL, new org.apache.thrift.meta_data.FieldMetaData("total", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BASE_RESP, new org.apache.thrift.meta_data.FieldMetaData("BaseResp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, top.itcat.rpc.base.BaseResp.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetPrescriptionItemResponse.class, metaDataMap);
  }

  public GetPrescriptionItemResponse() {
  }

  public GetPrescriptionItemResponse(
    int total,
    top.itcat.rpc.base.BaseResp BaseResp)
  {
    this();
    this.total = total;
    setTotalIsSet(true);
    this.BaseResp = BaseResp;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetPrescriptionItemResponse(GetPrescriptionItemResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPrescriptionItems()) {
      List<top.itcat.rpc.service.model.PrescriptionItem> __this__prescriptionItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionItem>(other.prescriptionItems.size());
      for (top.itcat.rpc.service.model.PrescriptionItem other_element : other.prescriptionItems) {
        __this__prescriptionItems.add(new top.itcat.rpc.service.model.PrescriptionItem(other_element));
      }
      this.prescriptionItems = __this__prescriptionItems;
    }
    if (other.isSetPrescriptionHerbItems()) {
      List<top.itcat.rpc.service.model.PrescriptionHerbItem> __this__prescriptionHerbItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionHerbItem>(other.prescriptionHerbItems.size());
      for (top.itcat.rpc.service.model.PrescriptionHerbItem other_element : other.prescriptionHerbItems) {
        __this__prescriptionHerbItems.add(new top.itcat.rpc.service.model.PrescriptionHerbItem(other_element));
      }
      this.prescriptionHerbItems = __this__prescriptionHerbItems;
    }
    this.total = other.total;
    if (other.isSetBaseResp()) {
      this.BaseResp = new top.itcat.rpc.base.BaseResp(other.BaseResp);
    }
  }

  public GetPrescriptionItemResponse deepCopy() {
    return new GetPrescriptionItemResponse(this);
  }

  @Override
  public void clear() {
    this.prescriptionItems = null;
    this.prescriptionHerbItems = null;
    setTotalIsSet(false);
    this.total = 0;
    this.BaseResp = null;
  }

  public int getPrescriptionItemsSize() {
    return (this.prescriptionItems == null) ? 0 : this.prescriptionItems.size();
  }

  public java.util.Iterator<top.itcat.rpc.service.model.PrescriptionItem> getPrescriptionItemsIterator() {
    return (this.prescriptionItems == null) ? null : this.prescriptionItems.iterator();
  }

  public void addToPrescriptionItems(top.itcat.rpc.service.model.PrescriptionItem elem) {
    if (this.prescriptionItems == null) {
      this.prescriptionItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionItem>();
    }
    this.prescriptionItems.add(elem);
  }

  public List<top.itcat.rpc.service.model.PrescriptionItem> getPrescriptionItems() {
    return this.prescriptionItems;
  }

  public GetPrescriptionItemResponse setPrescriptionItems(List<top.itcat.rpc.service.model.PrescriptionItem> prescriptionItems) {
    this.prescriptionItems = prescriptionItems;
    return this;
  }

  public void unsetPrescriptionItems() {
    this.prescriptionItems = null;
  }

  /** Returns true if field prescriptionItems is set (has been assigned a value) and false otherwise */
  public boolean isSetPrescriptionItems() {
    return this.prescriptionItems != null;
  }

  public void setPrescriptionItemsIsSet(boolean value) {
    if (!value) {
      this.prescriptionItems = null;
    }
  }

  public int getPrescriptionHerbItemsSize() {
    return (this.prescriptionHerbItems == null) ? 0 : this.prescriptionHerbItems.size();
  }

  public java.util.Iterator<top.itcat.rpc.service.model.PrescriptionHerbItem> getPrescriptionHerbItemsIterator() {
    return (this.prescriptionHerbItems == null) ? null : this.prescriptionHerbItems.iterator();
  }

  public void addToPrescriptionHerbItems(top.itcat.rpc.service.model.PrescriptionHerbItem elem) {
    if (this.prescriptionHerbItems == null) {
      this.prescriptionHerbItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionHerbItem>();
    }
    this.prescriptionHerbItems.add(elem);
  }

  public List<top.itcat.rpc.service.model.PrescriptionHerbItem> getPrescriptionHerbItems() {
    return this.prescriptionHerbItems;
  }

  public GetPrescriptionItemResponse setPrescriptionHerbItems(List<top.itcat.rpc.service.model.PrescriptionHerbItem> prescriptionHerbItems) {
    this.prescriptionHerbItems = prescriptionHerbItems;
    return this;
  }

  public void unsetPrescriptionHerbItems() {
    this.prescriptionHerbItems = null;
  }

  /** Returns true if field prescriptionHerbItems is set (has been assigned a value) and false otherwise */
  public boolean isSetPrescriptionHerbItems() {
    return this.prescriptionHerbItems != null;
  }

  public void setPrescriptionHerbItemsIsSet(boolean value) {
    if (!value) {
      this.prescriptionHerbItems = null;
    }
  }

  public int getTotal() {
    return this.total;
  }

  public GetPrescriptionItemResponse setTotal(int total) {
    this.total = total;
    setTotalIsSet(true);
    return this;
  }

  public void unsetTotal() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTAL_ISSET_ID);
  }

  /** Returns true if field total is set (has been assigned a value) and false otherwise */
  public boolean isSetTotal() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTAL_ISSET_ID);
  }

  public void setTotalIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTAL_ISSET_ID, value);
  }

  public top.itcat.rpc.base.BaseResp getBaseResp() {
    return this.BaseResp;
  }

  public GetPrescriptionItemResponse setBaseResp(top.itcat.rpc.base.BaseResp BaseResp) {
    this.BaseResp = BaseResp;
    return this;
  }

  public void unsetBaseResp() {
    this.BaseResp = null;
  }

  /** Returns true if field BaseResp is set (has been assigned a value) and false otherwise */
  public boolean isSetBaseResp() {
    return this.BaseResp != null;
  }

  public void setBaseRespIsSet(boolean value) {
    if (!value) {
      this.BaseResp = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRESCRIPTION_ITEMS:
      if (value == null) {
        unsetPrescriptionItems();
      } else {
        setPrescriptionItems((List<top.itcat.rpc.service.model.PrescriptionItem>)value);
      }
      break;

    case PRESCRIPTION_HERB_ITEMS:
      if (value == null) {
        unsetPrescriptionHerbItems();
      } else {
        setPrescriptionHerbItems((List<top.itcat.rpc.service.model.PrescriptionHerbItem>)value);
      }
      break;

    case TOTAL:
      if (value == null) {
        unsetTotal();
      } else {
        setTotal((Integer)value);
      }
      break;

    case BASE_RESP:
      if (value == null) {
        unsetBaseResp();
      } else {
        setBaseResp((top.itcat.rpc.base.BaseResp)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRESCRIPTION_ITEMS:
      return getPrescriptionItems();

    case PRESCRIPTION_HERB_ITEMS:
      return getPrescriptionHerbItems();

    case TOTAL:
      return Integer.valueOf(getTotal());

    case BASE_RESP:
      return getBaseResp();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRESCRIPTION_ITEMS:
      return isSetPrescriptionItems();
    case PRESCRIPTION_HERB_ITEMS:
      return isSetPrescriptionHerbItems();
    case TOTAL:
      return isSetTotal();
    case BASE_RESP:
      return isSetBaseResp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetPrescriptionItemResponse)
      return this.equals((GetPrescriptionItemResponse)that);
    return false;
  }

  public boolean equals(GetPrescriptionItemResponse that) {
    if (that == null)
      return false;

    boolean this_present_prescriptionItems = true && this.isSetPrescriptionItems();
    boolean that_present_prescriptionItems = true && that.isSetPrescriptionItems();
    if (this_present_prescriptionItems || that_present_prescriptionItems) {
      if (!(this_present_prescriptionItems && that_present_prescriptionItems))
        return false;
      if (!this.prescriptionItems.equals(that.prescriptionItems))
        return false;
    }

    boolean this_present_prescriptionHerbItems = true && this.isSetPrescriptionHerbItems();
    boolean that_present_prescriptionHerbItems = true && that.isSetPrescriptionHerbItems();
    if (this_present_prescriptionHerbItems || that_present_prescriptionHerbItems) {
      if (!(this_present_prescriptionHerbItems && that_present_prescriptionHerbItems))
        return false;
      if (!this.prescriptionHerbItems.equals(that.prescriptionHerbItems))
        return false;
    }

    boolean this_present_total = true;
    boolean that_present_total = true;
    if (this_present_total || that_present_total) {
      if (!(this_present_total && that_present_total))
        return false;
      if (this.total != that.total)
        return false;
    }

    boolean this_present_BaseResp = true && this.isSetBaseResp();
    boolean that_present_BaseResp = true && that.isSetBaseResp();
    if (this_present_BaseResp || that_present_BaseResp) {
      if (!(this_present_BaseResp && that_present_BaseResp))
        return false;
      if (!this.BaseResp.equals(that.BaseResp))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_prescriptionItems = true && (isSetPrescriptionItems());
    list.add(present_prescriptionItems);
    if (present_prescriptionItems)
      list.add(prescriptionItems);

    boolean present_prescriptionHerbItems = true && (isSetPrescriptionHerbItems());
    list.add(present_prescriptionHerbItems);
    if (present_prescriptionHerbItems)
      list.add(prescriptionHerbItems);

    boolean present_total = true;
    list.add(present_total);
    if (present_total)
      list.add(total);

    boolean present_BaseResp = true && (isSetBaseResp());
    list.add(present_BaseResp);
    if (present_BaseResp)
      list.add(BaseResp);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetPrescriptionItemResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPrescriptionItems()).compareTo(other.isSetPrescriptionItems());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrescriptionItems()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.prescriptionItems, other.prescriptionItems);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrescriptionHerbItems()).compareTo(other.isSetPrescriptionHerbItems());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrescriptionHerbItems()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.prescriptionHerbItems, other.prescriptionHerbItems);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotal()).compareTo(other.isSetTotal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.total, other.total);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBaseResp()).compareTo(other.isSetBaseResp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBaseResp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.BaseResp, other.BaseResp);
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
    StringBuilder sb = new StringBuilder("GetPrescriptionItemResponse(");
    boolean first = true;

    if (isSetPrescriptionItems()) {
      sb.append("prescriptionItems:");
      if (this.prescriptionItems == null) {
        sb.append("null");
      } else {
        sb.append(this.prescriptionItems);
      }
      first = false;
    }
    if (isSetPrescriptionHerbItems()) {
      if (!first) sb.append(", ");
      sb.append("prescriptionHerbItems:");
      if (this.prescriptionHerbItems == null) {
        sb.append("null");
      } else {
        sb.append(this.prescriptionHerbItems);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("total:");
    sb.append(this.total);
    first = false;
    if (!first) sb.append(", ");
    sb.append("BaseResp:");
    if (this.BaseResp == null) {
      sb.append("null");
    } else {
      sb.append(this.BaseResp);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'total' because it's a primitive and you chose the non-beans generator.
    if (BaseResp == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'BaseResp' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (BaseResp != null) {
      BaseResp.validate();
    }
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

  private static class GetPrescriptionItemResponseStandardSchemeFactory implements SchemeFactory {
    public GetPrescriptionItemResponseStandardScheme getScheme() {
      return new GetPrescriptionItemResponseStandardScheme();
    }
  }

  private static class GetPrescriptionItemResponseStandardScheme extends StandardScheme<GetPrescriptionItemResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetPrescriptionItemResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PRESCRIPTION_ITEMS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list106 = iprot.readListBegin();
                struct.prescriptionItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionItem>(_list106.size);
                top.itcat.rpc.service.model.PrescriptionItem _elem107;
                for (int _i108 = 0; _i108 < _list106.size; ++_i108)
                {
                  _elem107 = new top.itcat.rpc.service.model.PrescriptionItem();
                  _elem107.read(iprot);
                  struct.prescriptionItems.add(_elem107);
                }
                iprot.readListEnd();
              }
              struct.setPrescriptionItemsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRESCRIPTION_HERB_ITEMS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list109 = iprot.readListBegin();
                struct.prescriptionHerbItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionHerbItem>(_list109.size);
                top.itcat.rpc.service.model.PrescriptionHerbItem _elem110;
                for (int _i111 = 0; _i111 < _list109.size; ++_i111)
                {
                  _elem110 = new top.itcat.rpc.service.model.PrescriptionHerbItem();
                  _elem110.read(iprot);
                  struct.prescriptionHerbItems.add(_elem110);
                }
                iprot.readListEnd();
              }
              struct.setPrescriptionHerbItemsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 254: // TOTAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.total = iprot.readI32();
              struct.setTotalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 255: // BASE_RESP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.BaseResp = new top.itcat.rpc.base.BaseResp();
              struct.BaseResp.read(iprot);
              struct.setBaseRespIsSet(true);
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
      if (!struct.isSetTotal()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'total' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetPrescriptionItemResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.prescriptionItems != null) {
        if (struct.isSetPrescriptionItems()) {
          oprot.writeFieldBegin(PRESCRIPTION_ITEMS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.prescriptionItems.size()));
            for (top.itcat.rpc.service.model.PrescriptionItem _iter112 : struct.prescriptionItems)
            {
              _iter112.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.prescriptionHerbItems != null) {
        if (struct.isSetPrescriptionHerbItems()) {
          oprot.writeFieldBegin(PRESCRIPTION_HERB_ITEMS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.prescriptionHerbItems.size()));
            for (top.itcat.rpc.service.model.PrescriptionHerbItem _iter113 : struct.prescriptionHerbItems)
            {
              _iter113.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(TOTAL_FIELD_DESC);
      oprot.writeI32(struct.total);
      oprot.writeFieldEnd();
      if (struct.BaseResp != null) {
        oprot.writeFieldBegin(BASE_RESP_FIELD_DESC);
        struct.BaseResp.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetPrescriptionItemResponseTupleSchemeFactory implements SchemeFactory {
    public GetPrescriptionItemResponseTupleScheme getScheme() {
      return new GetPrescriptionItemResponseTupleScheme();
    }
  }

  private static class GetPrescriptionItemResponseTupleScheme extends TupleScheme<GetPrescriptionItemResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetPrescriptionItemResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.total);
      struct.BaseResp.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetPrescriptionItems()) {
        optionals.set(0);
      }
      if (struct.isSetPrescriptionHerbItems()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPrescriptionItems()) {
        {
          oprot.writeI32(struct.prescriptionItems.size());
          for (top.itcat.rpc.service.model.PrescriptionItem _iter114 : struct.prescriptionItems)
          {
            _iter114.write(oprot);
          }
        }
      }
      if (struct.isSetPrescriptionHerbItems()) {
        {
          oprot.writeI32(struct.prescriptionHerbItems.size());
          for (top.itcat.rpc.service.model.PrescriptionHerbItem _iter115 : struct.prescriptionHerbItems)
          {
            _iter115.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetPrescriptionItemResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.total = iprot.readI32();
      struct.setTotalIsSet(true);
      struct.BaseResp = new top.itcat.rpc.base.BaseResp();
      struct.BaseResp.read(iprot);
      struct.setBaseRespIsSet(true);
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list116 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.prescriptionItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionItem>(_list116.size);
          top.itcat.rpc.service.model.PrescriptionItem _elem117;
          for (int _i118 = 0; _i118 < _list116.size; ++_i118)
          {
            _elem117 = new top.itcat.rpc.service.model.PrescriptionItem();
            _elem117.read(iprot);
            struct.prescriptionItems.add(_elem117);
          }
        }
        struct.setPrescriptionItemsIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list119 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.prescriptionHerbItems = new ArrayList<top.itcat.rpc.service.model.PrescriptionHerbItem>(_list119.size);
          top.itcat.rpc.service.model.PrescriptionHerbItem _elem120;
          for (int _i121 = 0; _i121 < _list119.size; ++_i121)
          {
            _elem120 = new top.itcat.rpc.service.model.PrescriptionHerbItem();
            _elem120.read(iprot);
            struct.prescriptionHerbItems.add(_elem120);
          }
        }
        struct.setPrescriptionHerbItemsIsSet(true);
      }
    }
  }

}
