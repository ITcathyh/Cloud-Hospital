/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package top.itcat.rpc.service.model;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum ChargeItemStatusEnum implements org.apache.thrift.TEnum {
  Unpaid(0),
  Paid(1),
  Reversed(2),
  Reverse(3),
  Obsolete(4);

  private final int value;

  private ChargeItemStatusEnum(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ChargeItemStatusEnum findByValue(int value) { 
    switch (value) {
      case 0:
        return Unpaid;
      case 1:
        return Paid;
      case 2:
        return Reversed;
      case 3:
        return Reverse;
      case 4:
        return Obsolete;
      default:
        return null;
    }
  }
}