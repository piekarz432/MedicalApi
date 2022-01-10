package pl.com.britenet.javastepone.repository.model.medical_procedure;


import pl.com.britenet.javastepone.repository.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "medical_procedure")
public class MedicalProcedureEntity extends AbstractEntity {

    private String chapterId;
    private String chapterTitle;
    private String subChapterId;
    private String subChapterTitle;
    private String mainCategoryId;
    private String mainCategoryTitle;
    private String subCategoryId;
    private String subCategoryTitle;

    public MedicalProcedureEntity() {
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getSubChapterId() {
        return subChapterId;
    }

    public void setSubChapterId(String subChapterId) {
        this.subChapterId = subChapterId;
    }

    public String getSubChapterTitle() {
        return subChapterTitle;
    }

    public void setSubChapterTitle(String subChapterTitle) {
        this.subChapterTitle = subChapterTitle;
    }

    public String getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(String mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryTitle() {
        return mainCategoryTitle;
    }

    public void setMainCategoryTitle(String mainCategoryTitle) {
        this.mainCategoryTitle = mainCategoryTitle;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryTitle() {
        return subCategoryTitle;
    }

    public void setSubCategoryTitle(String subCategoryTitle) {
        this.subCategoryTitle = subCategoryTitle;
    }
}
