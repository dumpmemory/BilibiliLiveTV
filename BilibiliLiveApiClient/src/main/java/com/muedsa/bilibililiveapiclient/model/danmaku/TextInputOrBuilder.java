// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: danmaku.proto

package com.muedsa.bilibililiveapiclient.model.danmaku;

public interface TextInputOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.muedsa.bilibililiveapiclient.model.TextInput)
        com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>repeated string portraitPlaceholder = 1;</code>
     *
     * @return A list containing the portraitPlaceholder.
     */
    java.util.List<String>
    getPortraitPlaceholderList();

    /**
     * <code>repeated string portraitPlaceholder = 1;</code>
     *
     * @return The count of portraitPlaceholder.
     */
    int getPortraitPlaceholderCount();

    /**
     * <code>repeated string portraitPlaceholder = 1;</code>
     *
     * @param index The index of the element to return.
     * @return The portraitPlaceholder at the given index.
     */
    String getPortraitPlaceholder(int index);

    /**
     * <code>repeated string portraitPlaceholder = 1;</code>
     *
     * @param index The index of the element to return.
     * @return The portraitPlaceholder at the given index.
     */
    com.google.protobuf.ByteString
    getPortraitPlaceholderBytes(int index);

    /**
     * <code>repeated string landscapePlaceholder = 2;</code>
     *
     * @return A list containing the landscapePlaceholder.
     */
    java.util.List<String>
    getLandscapePlaceholderList();

    /**
     * <code>repeated string landscapePlaceholder = 2;</code>
     *
     * @return The count of landscapePlaceholder.
     */
    int getLandscapePlaceholderCount();

    /**
     * <code>repeated string landscapePlaceholder = 2;</code>
     *
     * @param index The index of the element to return.
     * @return The landscapePlaceholder at the given index.
     */
    String getLandscapePlaceholder(int index);

    /**
     * <code>repeated string landscapePlaceholder = 2;</code>
     *
     * @param index The index of the element to return.
     * @return The landscapePlaceholder at the given index.
     */
    com.google.protobuf.ByteString
    getLandscapePlaceholderBytes(int index);

    /**
     * <code>optional .com.muedsa.bilibililiveapiclient.model.RenderType renderType = 3;</code>
     *
     * @return Whether the renderType field is set.
     */
    boolean hasRenderType();

    /**
     * <code>optional .com.muedsa.bilibililiveapiclient.model.RenderType renderType = 3;</code>
     *
     * @return The enum numeric value on the wire for renderType.
     */
    int getRenderTypeValue();

    /**
     * <code>optional .com.muedsa.bilibililiveapiclient.model.RenderType renderType = 3;</code>
     *
     * @return The renderType.
     */
    RenderType getRenderType();

    /**
     * <code>optional bool placeholderPost = 4;</code>
     *
     * @return Whether the placeholderPost field is set.
     */
    boolean hasPlaceholderPost();

    /**
     * <code>optional bool placeholderPost = 4;</code>
     *
     * @return The placeholderPost.
     */
    boolean getPlaceholderPost();

    /**
     * <code>optional bool show = 5;</code>
     *
     * @return Whether the show field is set.
     */
    boolean hasShow();

    /**
     * <code>optional bool show = 5;</code>
     *
     * @return The show.
     */
    boolean getShow();
}