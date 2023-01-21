package tv.snews.ruby.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tv.snews.ruby.domain.AccountReceivable;
import tv.snews.ruby.web.dto.AccountReceivableDTO;
import tv.snews.ruby.web.dto.RatioAccountReceivableDTO;

@Mapper(
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    uses = { ChurchMapper.class, GLAccountReceivableMapper.class, UserMapper.class }
)
public interface AccountReceivableMapper extends EntityMapper<AccountReceivableDTO, AccountReceivable> {
    AccountReceivableMapper INSTANCE = Mappers.getMapper(AccountReceivableMapper.class);

    @Mapping(source = "checkFile.id", target = "checkFileId")
    @Mapping(source = "ratioParent.id", target = "parentId")
    AccountReceivableDTO toDto(AccountReceivable accountReceivable);

    @Mapping(source = "checkFileId", target = "checkFile.id")
    AccountReceivable toEntity(AccountReceivableDTO accountReceivableDTO);

    @Mapping(source = "ratioParent.id", target = "parentId")
    @Named("toRatio")
    List<RatioAccountReceivableDTO> toRatio(List<AccountReceivable> accountReceivable);
}
