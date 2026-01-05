package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
// <연결할 엔티티, ID의 타입>
public interface BoardRepository extends JpaRepository<Board, Long> {

}