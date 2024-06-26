package com.muedsa.bilibililivetv.player.video;

import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.text.CuesWithTiming;
import androidx.media3.extractor.text.SubtitleParser;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.muedsa.bilibililivetv.model.BilibiliSubtitle;
import com.muedsa.bilibililivetv.model.BilibiliSubtitleInfo;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@UnstableApi
public class BilibiliJsonSubtitleParser implements SubtitleParser {
    private final String TAG = BilibiliJsonSubtitleParser.class.getSimpleName();

    @Override
    public void parse(byte[] data, int offset, int length, OutputOptions outputOptions, Consumer<CuesWithTiming> output) {
        String json = new String(data, StandardCharsets.UTF_8);
        BilibiliSubtitleInfo bilibiliSubtitleInfo = JSON.parseObject(json, new TypeReference<BilibiliSubtitleInfo>() {
        });
        List<BilibiliSubtitle> list = bilibiliSubtitleInfo.getBody();
        for (BilibiliSubtitle bilibiliSubtitle : list) {
            Cue cue = new Cue.Builder()
                    .setText(bilibiliSubtitle.getContent())
                    .build();
            long startTimeUs = (long) (bilibiliSubtitle.getFrom() * 1000 * 1000);
            long endTimeUs = (long) (bilibiliSubtitle.getTo() * 1000 * 1000);
            output.accept(new CuesWithTiming(Collections.singletonList(cue), startTimeUs, endTimeUs - startTimeUs - 1));
        }
    }

    @Override
    public int getCueReplacementBehavior() {
        return 0;
    }
}
