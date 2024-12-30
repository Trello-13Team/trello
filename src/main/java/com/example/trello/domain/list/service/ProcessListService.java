package com.example.trello.domain.list.service;


import com.example.trello.domain.board.entity.Board;
import com.example.trello.domain.board.repository.BoardRepository;
import com.example.trello.domain.list.dto.ProcessListRequestDto;
import com.example.trello.domain.list.dto.ProcessListResponseDto;
import com.example.trello.domain.list.dto.UpdateProcessListRequestDto;
import com.example.trello.domain.list.entity.ProcessList;
import com.example.trello.domain.list.repository.ProcessListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessListService {

    private final ProcessListRepository processListRepository;
    private final BoardRepository boardRepository;

    public ProcessListResponseDto createList(Long boardId, ProcessListRequestDto listRequestDto, Long userId) {

        String title = listRequestDto.getTitle();

        if(title == null ) {
            throw new IllegalArgumentException("제목이 비었습니다.");
        }

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);
        Integer order = processListRepository.findLastOrder(boardId);
        if(order == null) {
            order = 0;
        }
        ProcessList processList = new ProcessList(title, order + 1,findBoard);
        ProcessList saveList = processListRepository.save(processList);

        return ProcessListResponseDto.toDto(saveList);
    }
    public void deleteList(Long listId, Long userId) {

        ProcessList deleteProcessList = processListRepository.findByIdOrElseThrow(listId);

        processListRepository.delete(deleteProcessList);
    }

    public ProcessListResponseDto updateList(Long boardId, Long listId, UpdateProcessListRequestDto updateProcessListRequestDto, Long userId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        ProcessList findProcessList = processListRepository.findByIdOrElseThrow(listId);

        List<ProcessList> processLists = processListRepository.findListByBoardId(findBoard.getId());

        Integer firstOrder = findProcessList.getOrder();
        Integer changeOrder = updateProcessListRequestDto.getOrder();

        if(firstOrder < changeOrder) {

            for (ProcessList change : processLists) {

                if (firstOrder < change.getOrder() && change.getOrder() <= updateProcessListRequestDto.getOrder()) {
                    change.changeOrder(change.getOrder()-1);
                }
            }
        } else {
            for (ProcessList change : processLists) {

                if (updateProcessListRequestDto.getOrder() <= change.getOrder() && change.getOrder() < firstOrder) {
                    change.changeOrder(change.getOrder()+1);
                }
            }
        }

        findProcessList.update(updateProcessListRequestDto.getTitle(), updateProcessListRequestDto.getOrder());
        processListRepository.save(findProcessList);

        return ProcessListResponseDto.toDto(findProcessList);
    }
}
