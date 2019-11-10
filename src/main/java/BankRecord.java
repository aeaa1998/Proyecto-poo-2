import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="bank_records")
public class BankRecord extends Models{
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "record_type_id")
    private int recordTypeId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "sponsor_id")
    private int sponsorId;

    @Transient
    private RecordType recordType;

    public BankRecord() {
    }

    public BankRecord(Double amount, int recordTypeId, Date createdAt, RecordType recordType, int sponsorId) {
        this.amount = amount;
        this.recordTypeId = recordTypeId;
        this.createdAt = createdAt;
        this.recordType = recordType;
        this.sponsorId = sponsorId;
    }

    public int getId() {
        return id;
    }
    public int getSponsorId() {
        return this.sponsorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setSponsorId(int id) {
        this.sponsorId = id;
    }

    public int getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(int recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }
}
