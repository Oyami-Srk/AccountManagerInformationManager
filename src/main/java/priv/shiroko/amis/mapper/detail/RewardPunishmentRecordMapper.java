package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.RewardPunishmentRecord;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【reward_punishment_record】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:01
 * @Entity priv.shiroko.amis.entity.detail.RewardPunishmentRecord
 */
@Mapper
public interface RewardPunishmentRecordMapper extends BasicMapper<RewardPunishmentRecord> {

    public List<RewardPunishmentRecord> get(@Param("param") RewardPunishmentRecord param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM reward_punishment_record WHERE id = #{id};")
    public RewardPunishmentRecord getById(@Param("id") int id);

    public int count(@Param("param") RewardPunishmentRecord param);

    @Select("SELECT EXISTS(SELECT 1 FROM reward_punishment_record WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM reward_punishment_record WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") RewardPunishmentRecord param);

    public void add(@Param("param") RewardPunishmentRecord param);
}




