package com.pzk.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {
    /**
     * 查出的questionDto集合
     */
    private List<QuestionDto> questionDtoList;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    //当前页
    private Integer page;
    //展示到页面的 列表
    private List<Integer> pages = new ArrayList<>();
    //总页数
    private Integer totalPage;

    public void setPageInation(Integer count, Integer page, Integer size) {

        Integer totalPage ;
        if(count % size ==0){
            totalPage = count/size;
        }else{
            totalPage = count/size + 1;
        }
        this.totalPage = totalPage;

        if(page < 1){
            page = 1;
        }
        if(page >totalPage){
            page = totalPage;
        }
        this.page = page;

        pages.add(page);
        for (int i = 1; i <= 3; i++){
            if(page - i >0){
                pages.add(0, page - i);
            }

            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }

        //是否是上一页
        if(page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }

        //是否展示下一页
        if(page == totalPage){
            showEndPage = false;
        }else{
            showEndPage = true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }

        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
