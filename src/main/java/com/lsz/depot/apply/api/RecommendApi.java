package com.lsz.depot.apply.api;

import com.lsz.core.dto.SuggestionDTO;
import com.lsz.depot.apply.service.SuggestionService;
import com.lsz.core.common.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ex-taozhangyi on 2017/9/5.
 */
@RestController
@RequestMapping(value = "/api/recommends")
public class RecommendApi {

    @Autowired
    private SuggestionService suggestionService;

    /**
     * 新增意见反馈
     *
     * @return 成功返回true
     */
    @RequestMapping(value = "/suggestions/add", method = RequestMethod.POST)
    public ResponseInfo<SuggestionDTO> createSuggestion(@RequestBody SuggestionDTO suggestionDTO) {
        return ResponseInfo.assertion(suggestionService.createSuggestion(suggestionDTO));

    }

    /**
     * 修改意见反馈
     *
     * @return SuggestionDTO
     */
    @RequestMapping(value = "/suggestions/update", method = RequestMethod.POST)
    public ResponseInfo<SuggestionDTO> updateSuggestion(@RequestBody SuggestionDTO suggestionDTO, @RequestParam String uuid) {
        return ResponseInfo.assertion(suggestionService.updateSuggestion(suggestionDTO, uuid));

    }

    /**
     * 修改意见反馈
     *
     * @return SuggestionDTO
     */
    @RequestMapping(value = "/suggestions/findByUuid")
    public ResponseInfo<SuggestionDTO> findSuggestionByUuid(@RequestParam String uuid) {
        return ResponseInfo.assertion(suggestionService.findBySuggestionUuid(uuid));

    }
}
