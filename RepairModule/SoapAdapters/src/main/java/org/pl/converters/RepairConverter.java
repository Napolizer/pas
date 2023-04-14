package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.model.*;

@ApplicationScoped
public class RepairConverter {
    @Inject
    private ClientConverter clientConverter;
    @Inject
    private HardwareConverter hardwareConverter;
    @Inject
    private DateRangeConverter dateRangeConverter;
    public RepairSoap convert(Repair repair) {
        if (repair == null) return null;
        try {
            ClientSoap clientEnt = clientConverter.convert(repair.getClient());
            HardwareSoap hardwareEnt = hardwareConverter.convert(repair.getHardware());
            return new RepairSoap(repair.getId(), repair.getArchive(), clientEnt, hardwareEnt,
                    dateRangeConverter.convert(repair.getDateRange()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Repair convert(RepairSoap repairSoap) {
        if (repairSoap == null) return null;
        try {
            Client client = clientConverter.convert(repairSoap.getClient());
            Hardware hardware = hardwareConverter.convert(repairSoap.getHardware());
            return Repair.builder()
                    .id(repairSoap.getId())
                    .archive(repairSoap.getArchive())
                    .client(client)
                    .hardware(hardware)
                    .dateRange(dateRangeConverter.convert(repairSoap.getDateRange()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
