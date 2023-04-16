package org.pl.repair.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.repair.module.model.*;

@ApplicationScoped
public class RepairConverter {
    @Inject
    private ClientConverter clientConverter;
    @Inject
    private HardwareConverter hardwareConverter;
    @Inject
    private DateRangeConverter dateRangeConverter;
    public RepairRest convert(Repair repair) {
        if (repair == null) return null;
        try {
            ClientRest clientEnt = clientConverter.convert(repair.getClient());
            HardwareRest hardwareEnt = hardwareConverter.convert(repair.getHardware());
            return RepairRest.builder()
                    .id(repair.getId())
                    .archive(repair.getArchive())
                    .client(clientEnt)
                    .hardware(hardwareEnt)
                    .dateRange(dateRangeConverter.convert(repair.getDateRange()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Repair convert(RepairRest repairRest) {
        if (repairRest == null) return null;
        try {
            Client client = clientConverter.convert(repairRest.getClient());
            Hardware hardware = hardwareConverter.convert(repairRest.getHardware());
            return Repair.builder()
                    .id(repairRest.getId())
                    .archive(repairRest.getArchive())
                    .client(client)
                    .hardware(hardware)
                    .dateRange(dateRangeConverter.convert(repairRest.getDateRange()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
