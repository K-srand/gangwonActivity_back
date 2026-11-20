package com.multicampus.gangwonActivity.repository;

import com.multicampus.gangwonActivity.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    BoardImage findByBoardNo(Long boardNo);

    List<BoardImage> findByImageAddress(String imageAddress);

//    @Transactional
//    void deleteByBoardNo(Long boardNo);

}
