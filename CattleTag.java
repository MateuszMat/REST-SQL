//SK
package dao.repeat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// @XmlElement

@XmlRootElement (name = "cattletag")
@XmlType(propOrder={"tag_id", "gender", "breed", "age"})
public class CattleTag {
    private int tag_id;
    private String gender, breed;
    private int age;

    public CattleTag() {
    }

    public CattleTag(int tag_id, String gender, String breed, int age) {
        this.tag_id = tag_id;
        this.gender = gender;
        this.breed = breed;
        this.age = age;
    }

    @XmlElement
    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    @XmlElement
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @XmlElement
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CattleTag{" + "tag_id=" + tag_id + ", gender=" + gender + ", breed=" + breed + ", age=" + age + '}';
    }
    
    
}
