package com.ssafy.enjoytrip.board.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "CommentListDto : 해당 글의 댓글 목록", description = "해당 글의 댓글 목록를 나타낸다.")
public class CommentListDto {
	@ApiModelProperty(value = "댓글 목록")
	private List<CommentDto> commentList;
}
