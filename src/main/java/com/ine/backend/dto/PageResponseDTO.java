package com.ine.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageResponseDTO<T> {
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean first;
	private boolean last;
	private boolean empty;

	public static <T> PageResponseDTO<T> from(org.springframework.data.domain.Page<T> page) {
		PageResponseDTO<T> response = new PageResponseDTO<>();
		response.setContent(page.getContent());
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setFirst(page.isFirst());
		response.setLast(page.isLast());
		response.setEmpty(page.isEmpty());
		return response;
	}
}
