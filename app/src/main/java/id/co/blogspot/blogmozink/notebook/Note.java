package id.co.blogspot.blogmozink.notebook;

/**
 * Created by Axoriea-LPMX on 12/11/2016.
 */

public class Note {
    private String title,message;
    private long noteId,dateCeatedMilli;
    private Category category;
    public enum Category {PERSONAL,TECHNICAL,QUOTE,FINANCE}

    public Note (String title, String message, Category category){
        this.title=title;
        this.message=message;
        this.category=category;
        this.noteId=0;
        this.dateCeatedMilli=0;
    }

    public Note(String title,String message,Category category,long noteId, long dateCeatedMilli){
        this.title=title;
        this.message=message;
        this.category=category;
        this.noteId=noteId;
        this.dateCeatedMilli=dateCeatedMilli;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Category getCategory() {
        return category;
    }

    public long getDate(){return dateCeatedMilli;}
    public long getID(){return noteId;}

    public String toString(){
        return "ID: "+ noteId+" Title: "+ title+" Message: "+ message +" IconID: "+ category.name()+" Date: ";
    }

    public int getAssociatedDrawable(){
        return categoryToDrawabale(category);
    }

    public static int categoryToDrawabale(Category noteCategory){

        switch (noteCategory){
            case PERSONAL:
                return R.drawable.p;
            case TECHNICAL:
                return R.drawable.t;
            case FINANCE:
                return R.drawable.f;
            case QUOTE:
                return R.drawable.q;
        }
        return R.drawable.p;

    }

}
