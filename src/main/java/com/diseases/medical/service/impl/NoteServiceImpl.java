package com.diseases.medical.service.impl;

import com.diseases.medical.dao.NoteDao;
import com.diseases.medical.pojo.Note;
import com.diseases.medical.pojo.Note_Comment;
import com.diseases.medical.service.NoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Resource
    private NoteDao noteDao;

    @Override
    public List<Note> getNoteByType(String note_type) {
        return noteDao.getNoteByType(note_type);
    }

    @Override
    public Note getNoteById(String note_id) {
        return noteDao.getNoteById(note_id);
    }

    @Override
    public int updateNote(Note note) {
        return noteDao.updateNote(note);
    }

    @Override
    public List<Note_Comment> getNoteCommentsByNote_id(String note_id) {
        return noteDao.getNoteCommentsByNote_id(note_id);
    }

    @Override
    public int saveComment(Note_Comment note_comment) {
        return noteDao.saveComment(note_comment);
    }

    @Override
    public int saveNote(Note note) {
        return noteDao.saveNote(note);
    }

    @Override
    public List<Note> getNoteList() {
        return noteDao.getNoteList();
    }
}
