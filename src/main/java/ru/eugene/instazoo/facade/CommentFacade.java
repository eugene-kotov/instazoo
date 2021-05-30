package ru.eugene.instazoo.facade;

import org.springframework.stereotype.Component;
import ru.eugene.instazoo.dto.CommentDTO;
import ru.eugene.instazoo.entity.Comment;

@Component
public class CommentFacade {
    public CommentDTO commentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUsername(comment.getUsername());
        commentDTO.setMessage(comment.getMessage());

        return commentDTO;
    }
}
