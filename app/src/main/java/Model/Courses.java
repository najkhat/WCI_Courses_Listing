package Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Courses implements Parcelable{
    private String code;
    private String name;
    private int grade;
    private String subject;
    private String type;
    private String description;

    public Courses() {
    }

    public Courses(Parcel in) {
        code = in.readString();
        name = in.readString();
        grade = in.readInt();
        subject = in.readString();
        type = in.readString();
        description = in.readString();
    }

    public Courses(String code, String name, int grade, String subject, String type, String description) {
        this.code = code;
        this.name = name;
        this.grade = grade;
        this.subject = subject;
        this.type = type;
        this.description = description;
    }

    public static final Creator<Courses> CREATOR = new Creator<Courses>() {
        @Override
        public Courses createFromParcel(Parcel in) {
            return new Courses(in);
        }

        @Override
        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", subject='" + subject + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeInt(grade);
        dest.writeString(subject);
        dest.writeString(type);
        dest.writeString(description);
    }
}
