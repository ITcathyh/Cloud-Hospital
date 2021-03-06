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

public enum DoctorTitleEnum implements org.apache.thrift.TEnum {
  Case(0),
  House(1),
  AssistantDirector(2),
  Director(3);

  private final int value;

  private DoctorTitleEnum(int value) {
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
  public static DoctorTitleEnum findByValue(int value) { 
    switch (value) {
      case 0:
        return Case;
      case 1:
        return House;
      case 2:
        return AssistantDirector;
      case 3:
        return Director;
      default:
        return null;
    }
  }
}
