package lambdas;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

class ResourceState {
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_DESC = "desc";
    public static final String FIELD_NAME_CUSTOM_PROPERTIES = "customProperties";
    public static final String FIELD_NAME_TENANT_LINKS = "tenantLinks";
    public static final String FIELD_NAME_GROUP_LINKS = "groupLinks";
    public static final String FIELD_NAME_TAG_LINKS = "tagLinks";
    public static final String FIELD_NAME_ENDPOINT_LINKS = "endpointLinks";
    public static final String FIELD_NAME_REGION_ID = "regionId";
    public static final String FIELD_NAME_CREATION_TIME_MICROS = "creationTimeMicros";
    public static final String FIELD_NAME_COMPUTE_HOST_LINK = "computeHostLink";
    public String id;
    public String name;
    public String desc;
    public Map<String, String> customProperties;
    public List<String> tenantLinks;
    public Set<String> groupLinks;
    public Set<String> tagLinks;
    public Long creationTimeMicros;
    public String regionId;
    public Set<String> endpointLinks;

}

class StorageProfile extends ResourceState {
    public static final String FIELD_NAME_PROVISIONING_REGION_LINK = "provisioningRegionLink";
    public String provisioningRegionLink;
    public List<StorageItem> storageItems;
}

class StorageItem {
    public String resourceGroupLink;
    public String storageDescriptionLink;
    public String name;
    public Set<String> tagLinks;
    public Map<String, String> diskProperties;
    public boolean defaultItem;
    public Boolean supportsEncryption;

}

class Metric {
    private String type, subType, endpointType;

    public String getType() {
        return type;
    }

    public Metric setType(String type) {
        this.type = type;
        return this;
    }

    public String getSubType() {
        return subType;
    }

    public Metric setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public String getEndpointType() {
        return endpointType;
    }

    public Metric setEndpointType(String endpointType) {
        this.endpointType = endpointType;
        return this;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "type='" + type + '\'' +
                ", subType='" + subType + '\'' +
                ", endpointType='" + endpointType + '\'' +
                '}';
    }
}

public class CollectorTest2 {
    public static void main(String[] args) {
        StorageItem awsItem1 = new StorageItem();
        awsItem1.diskProperties = new HashMap<>();
        awsItem1.diskProperties.put("deviceType", "ebs");
        awsItem1.diskProperties.put("volumeType", "io1");
        awsItem1.diskProperties.put("iops", "400");

        StorageItem awsItem2 = new StorageItem();
        awsItem2.diskProperties = new HashMap<>();
        awsItem2.diskProperties.put("deviceType", "ebs");
        awsItem2.diskProperties.put("volumeType", "gp2");
        awsItem2.diskProperties.put("iops", "400");

        StorageItem awsItem3 = new StorageItem();
        awsItem3.diskProperties = new HashMap<>();
        awsItem3.diskProperties.put("deviceType", "ebs");
        awsItem3.diskProperties.put("volumeType", "standard");
        awsItem3.diskProperties.put("iops", "400");

        StorageItem awsItem4 = new StorageItem();
        awsItem4.diskProperties = new HashMap<>();
        awsItem4.diskProperties.put("deviceType", "ebs");
        awsItem4.diskProperties.put("volumeType", "io1");
        awsItem4.diskProperties.put("iops", "400");

        StorageItem awsItem5 = new StorageItem();
        awsItem5.diskProperties = new HashMap<>();
        awsItem5.diskProperties.put("deviceType", "instance_store");
        awsItem5.diskProperties.put("iops", "400");

        StorageProfile awsStorageProfile1 = new StorageProfile();
        awsStorageProfile1.endpointLinks = new TreeSet<>(Arrays.asList("aws"));
        awsStorageProfile1.id = "awsStorageProfile1";
        awsStorageProfile1.storageItems = Arrays.asList(awsItem1, awsItem2, awsItem3);

        StorageProfile awsStorageProfile2 = new StorageProfile();
        awsStorageProfile2.endpointLinks = new TreeSet<>(Arrays.asList("aws"));
        awsStorageProfile2.id = "awsStorageProfile2";
        awsStorageProfile2.storageItems = Arrays.asList(awsItem4, awsItem5);

        StorageItem azureItem1 = new StorageItem();
        azureItem1.diskProperties = new HashMap<>();
        azureItem1.diskProperties.put("azureManagedDiskType", "Standard_LRS");

        StorageItem azureItem2 = new StorageItem();
        azureItem2.diskProperties = new HashMap<>();
        azureItem2.diskProperties.put("azureStorageAccountType", "Standard_LRS");
        azureItem2.diskProperties.put("azureOsDiskCaching", "None");
        azureItem2.diskProperties.put("azureDataDiskCaching", "ReadWrite");

        StorageItem azureItem3 = new StorageItem();
        azureItem3.diskProperties = new HashMap<>();
        azureItem3.diskProperties.put("azureStorageAccountType", "Standard_LRS");
        azureItem3.diskProperties.put("azureOsDiskCaching", "None");
        azureItem3.diskProperties.put("azureDataDiskCaching", "ReadWrite");

        StorageProfile azureStorageProfile1 = new StorageProfile();
        azureStorageProfile1.endpointLinks = new TreeSet<>(Arrays.asList("azure"));
        azureStorageProfile1.id = "azureStorageProfile1";
        azureStorageProfile1.storageItems = Arrays.asList(azureItem1, azureItem2);

        StorageProfile azureStorageProfile2 = new StorageProfile();
        azureStorageProfile2.endpointLinks = new TreeSet<>(Arrays.asList("azure"));
        azureStorageProfile2.id = "azureStorageProfile2";
        azureStorageProfile2.storageItems = Arrays.asList(azureItem3);

        List<StorageProfile> storageProfiles = Arrays.asList(awsStorageProfile1, awsStorageProfile2, azureStorageProfile1, azureStorageProfile2);

        Map<String, Map<String, Integer>> itemsByProfileAndType = storageProfiles.stream().collect(
                groupingBy(profile -> profile.endpointLinks.stream().findAny().get(),
                        groupingBy(profile -> profile.id, summingInt(profile -> profile.storageItems.size()))));
        System.out.println(itemsByProfileAndType);

        storageProfiles.stream().map(profile -> {
            profile.name = "standard";
            return profile;
        }).collect(toList());

        Map<String, Integer> deviceCount = new HashMap<>();
        Map<String, Map<String, Integer>> summary = new HashMap<>();
        storageProfiles.stream().forEach(profile -> {
            System.out.println(profile.id);
            System.out.println("    " + profile.endpointLinks);
            String endpointType = profile.endpointLinks.stream().findAny().get();
            profile.storageItems.forEach(item -> {
                System.out.println("    storageItem");
                item.diskProperties.forEach((k, v) -> System.out.println("           " + k + "," + v));
                item.diskProperties.forEach((k, v) -> {
                            if ("azureManagedDiskType".equals(k)
                                    || "azureStorageAccountType".equals(k)
                                    || "deviceType".equals(k)) {
                                Map<String, Integer> devCount = summary.getOrDefault(endpointType, new HashMap<>());
                                devCount.compute(v, (tokenKey, oldValue) -> oldValue == null ? 1 : oldValue + 1);
                                summary.putIfAbsent(endpointType, devCount);
                            }
                        }
                );
            });
        });
        System.out.println(summary);

        summary.forEach((k, v) -> {
            String key = Collections.max(v.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println(k + " max provisioned disk = " + key);
        });

        Map<String, String> storageItemProps = new HashMap<>();
        storageProfiles.forEach(profile -> {
            String endpointType = profile.endpointLinks.stream().findAny().get();
            profile.storageItems.forEach(item -> {
                storageItemProps.putAll(item.diskProperties);
            });
        });
        System.out.println(storageItemProps);

        Map<String, List<StorageProfile>> profilesByType = storageProfiles.stream().collect(groupingBy(profile -> profile.endpointLinks.stream().findAny().get()));
        Map<String, Map<String, Integer>> itemCountsByProfileAndCloud = new HashMap<>();
        profilesByType.entrySet().forEach(entry -> {
            System.out.println(entry.getKey());
            entry.getValue().forEach(profile -> System.out.println("    " + profile.id));
            itemCountsByProfileAndCloud.computeIfAbsent(entry.getKey(), s -> entry.getValue().stream().collect(groupingBy(e -> e.name, summingInt(e -> e.storageItems.size()))));
        });
        System.out.println("itemCountsByProfileAndCloud = "+itemCountsByProfileAndCloud);


        List<Metric> metrics = new ArrayList<>();

        profilesByType.get("aws").stream().forEach(profile -> {
            profile.storageItems.stream().forEach(item -> {
                String type = item.diskProperties.get("deviceType")!=null?item.diskProperties.get("deviceType"):"";
                String subType = item.diskProperties.get("volumeType")!=null?item.diskProperties.get("volumeType"):"";
                metrics.add(new Metric().setEndpointType("aws")
                        .setType(type)
                        .setSubType(subType));
            });
        });

        System.out.println(metrics);

        Map<String, Map<String, Map<String, Long>>> awsSummary = metrics.stream().collect(groupingBy(Metric::getEndpointType, groupingBy(Metric::getType, groupingBy(Metric::getSubType, counting()))));
        System.out.println(awsSummary);
    }
}
