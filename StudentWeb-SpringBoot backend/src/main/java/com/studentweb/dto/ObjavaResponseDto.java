package com.studentweb.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObjavaResponseDto {
	private List<ObjavaDto> posts;
	private Integer currentpage;
	private Integer totalpage;
	private long totalposts;
	private boolean islastpage;
}
