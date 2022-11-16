// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: danmaku.proto

package com.muedsa.bilibililiveapiclient.model.danmaku;

/**
 * Protobuf enum {@code com.muedsa.bilibililiveapiclient.model.ToastBizType}
 */
public enum ToastBizType
        implements com.google.protobuf.Internal.EnumLite {
    /**
     * <code>ToastBizTypeNone = 0;</code>
     */
    ToastBizTypeNone(0),
    /**
     * <code>ToastBizTypeEncourage = 1;</code>
     */
    ToastBizTypeEncourage(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>ToastBizTypeNone = 0;</code>
     */
    public static final int ToastBizTypeNone_VALUE = 0;
    /**
     * <code>ToastBizTypeEncourage = 1;</code>
     */
    public static final int ToastBizTypeEncourage_VALUE = 1;


    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException(
                    "Can't get the number of an unknown enum value.");
        }
        return value;
    }

    /**
     * @param value The number of the enum to look for.
     * @return The enum associated with the given number.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @Deprecated
    public static ToastBizType valueOf(int value) {
        return forNumber(value);
    }

    public static ToastBizType forNumber(int value) {
        switch (value) {
            case 0:
                return ToastBizTypeNone;
            case 1:
                return ToastBizTypeEncourage;
            default:
                return null;
        }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ToastBizType>
    internalGetValueMap() {
        return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<
            ToastBizType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<ToastBizType>() {
                @Override
                public ToastBizType findValueByNumber(int number) {
                    return ToastBizType.forNumber(number);
                }
            };

    public static com.google.protobuf.Internal.EnumVerifier
    internalGetVerifier() {
        return ToastBizTypeVerifier.INSTANCE;
    }

    private static final class ToastBizTypeVerifier implements
            com.google.protobuf.Internal.EnumVerifier {
        static final com.google.protobuf.Internal.EnumVerifier INSTANCE = new ToastBizTypeVerifier();

        @Override
        public boolean isInRange(int number) {
            return ToastBizType.forNumber(number) != null;
        }
    }

    ;

    private final int value;

    private ToastBizType(int value) {
        this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:com.muedsa.bilibililiveapiclient.model.ToastBizType)
}
