package com.lsz.depot.apply.service;

import com.lsz.depot.apply.base.dao.ProductInfoDao;
import com.lsz.depot.apply.po.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductInfoDao productInfoDao;

    //    String s1 = "abc";
//    String s2 = "abcd";
//    String s3 = "abcdfg";
//    String s4 = "1bcdfg";
//    String s5 = "cdfg";
//System.out.println( s1.compareTo(s2) ); // -1 (前面相等,s1长度小1)
//System.out.println( s1.compareTo(s3) ); // -3 (前面相等,s1长度小3)
//System.out.println( s1.compareTo(s4) ); // 48 ("a"的ASCII码是97,"1"的的ASCII码是49,所以返回48)
//System.out.println( s1.compareTo(s5) ); // -2 ("a"的ASCII码是97,"c"的ASCII码是99,所以返回-2)
    public static ProductInfo findMax(List<ProductInfo> list) {
        ProductInfo max = null;
        for (ProductInfo productInfo : list) {
            if (max == null) {
                max = productInfo;
            } else if (max.getNumber().compareTo(productInfo.getNumber()) < 0) {
                max = productInfo;
            }
        }
        return max;
    }

    public static String numberAdd(String number) {
        String[] sArr = number.split("");
        String nStr = "";
        String leftStr = "";
        for (int i = sArr.length - 1; i >= 0; i--) {
            if (isNumber(sArr[i])) {
                nStr = sArr[i] + nStr;
            } else {
                leftStr = number.substring(0, i + 1);
                break;
            }
        }
        if (StringUtils.isEmpty(nStr)) {
            return number + "1";
        } else {
            String value = String.valueOf(Integer.parseInt(nStr) + 1);
            int n = nStr.length() - value.length();
            if (n > 0) {
                for (int i = 0; i < n; i++) {
                    value = "0" + value;
                }
            }
            return leftStr + value;
        }
    }

    public static Boolean isNumber(String str) {
        if ("0123456789".contains(str)) {
            return true;
        }
        return false;
    }

    public String findMaxNumber(String type) {
        //要校验重复  所以必须查两次，没必要优化
        List<ProductInfo> productInfos = productInfoDao.findAll();
        if (CollectionUtils.isEmpty(productInfos)) {
            return "10000";
        }

        if (StringUtils.isEmpty(type)) {
            return numberAdd(findMax(productInfos).getNumber());
        } else {
            String number = "";
            List<ProductInfo> byType = productInfoDao.findByType(type);
            if (!CollectionUtils.isEmpty(byType)) {
                number = numberAdd(findMax(byType).getNumber());
                for (ProductInfo productInfo : productInfos) {
                    if (productInfo.getNumber().equals(number)) {
                        number = "";
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(number)) {
                return number;
            }
            return numberAdd(findMax(productInfos).getNumber());
        }
    }
}
