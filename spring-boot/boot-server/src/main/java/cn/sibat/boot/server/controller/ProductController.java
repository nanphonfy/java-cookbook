package cn.sibat.boot.server.controller;

import cn.sibat.boot.server.entity.Product;
import cn.sibat.boot.server.entity.ProductEarningRate;
import cn.sibat.boot.server.exception.RRException;
import cn.sibat.boot.server.service.ProductService;
import cn.sibat.boot.server.util.FastJsonUtil;
import cn.sibat.boot.server.util.Status;
import cn.sibat.boot.server.util.common.RegValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/13
 */
@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController{
    @Autowired
    private ProductService productService;

    /**
     * 根据id查询理财产品
     * localhost:8997/ebase-server/product/1
     */
    @GetMapping("/{id}")
    public String findProductById(@PathVariable("id") String id) {
        // 验证产品id
        if (!RegValidationUtil.validateIsNumber(id)) {
            throw new RRException("id is not a number...");
        }
        Product product = productService.getById(Long.valueOf(id));
        return FastJsonUtil.getOkDataResponse(product);
    }

    /**
     * 查询所有商品类
     * localhost:8997/ebase-server/product/list
     */
    @GetMapping("/list")
    public String findAllProduct() {
        try {
            List<Product> list = productService.getAllProducts();
            logger.info("{}",list.size());
            return FastJsonUtil.getOkDataResponse(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return FastJsonUtil.error(e.getMessage());
        }
    }

    /**
     * 删除理财产品
     * localhost:8997/ebase-server/product/1
     * @param proId
     */
    @DeleteMapping("/{proId}")
    public String deleteProductByProId(@PathVariable("proId")Long proId) {
        try {
            productService.deleteProductByProId(proId);
            return FastJsonUtil.ok();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return FastJsonUtil.error(e.getMessage());
        }
    }

    /**
     * 添加理财产品
     * localhost:8997/ebase-server/product/1
     *
     */
    @PostMapping("/save")
    public String addProduct(@RequestBody Product product,String proEarningRates) {
        try {
            // 处理产品利率数据
            Map rates = null;
            try {
                // 将请求参数转换成Map集合
                rates = FastJsonUtil.jsonToPojo(proEarningRates, Map.class);
                Set<String> keys = null;
                if (null != rates) {
                    keys = rates.keySet();
                    List<ProductEarningRate> list = new ArrayList<ProductEarningRate>();
                    // 封装利率实体集合
                    for (String k : keys) {
                        // 判断key是否是数字
                        if (!RegValidationUtil.validateIsNumber(k)) {
                            return FastJsonUtil.getResponse(Status.PARA_ERROR,"月份不是数字","").toString();
                        }
                        ProductEarningRate per = new ProductEarningRate();
                        per.setIncomeRate(Double.parseDouble(String.valueOf(rates.get(k))));
                        per.setMonth(Integer.valueOf(k));
                        //把获取到的数据封装起来
                        per.setProductId((int) product.getProId());
                        list.add(per);
                    }
                    product.setProEarningRate(list);
                } else {
                    return FastJsonUtil.error("参数错误：proEarningRate:" + proEarningRates);
                }
            } catch (Exception e1) {
                logger.error("系统异常：{}", e1);
                return FastJsonUtil.error("利率不符要求");
            }
            productService.saveProduct(product);
            return FastJsonUtil.ok();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return FastJsonUtil.error(e.getMessage());
        }
    }

    /**
     * 修改操作
     * localhost:8997/ebase-server/product/update
     * proEarningRates：{12:3,15:3.2......}——得到请求参数,可以使用模型驱动，但是利率没有封装
     */
    @GetMapping("/update")
    public String modifyProduct(@RequestBody Product product,String proEarningRates) {
        try {
            // 验证商品id是否是数字
            if (!RegValidationUtil.validateIsNumber(String.valueOf(product.getProId()))) {
                return FastJsonUtil.getResponse(Status.PARA_ERROR,"商品id不是数字","").toString();
            }
            if (!RegValidationUtil.validateIsNumber(product.getClosedPeriod() + "")) {
                return FastJsonUtil.getResponse(Status.PARA_ERROR,"转让封闭期不是数字","").toString();
            }
            if (!RegValidationUtil.validateIsNumber(String.valueOf(product.getStatus()))) {
                return FastJsonUtil.getResponse(Status.PARA_ERROR,"状态不是数字","").toString();
            }
            if (!RegValidationUtil.validateIsNumber(String.valueOf(product.getProUpperInvest()))) {
                return FastJsonUtil.getResponse(Status.PARA_ERROR,"产品投资上限不是数字","").toString();
            }
            if (!RegValidationUtil.validateIsNumber(String.valueOf(product.getProLowerInvest()))) {
                return FastJsonUtil.getResponse(Status.PARA_ERROR,"产品起投金额不是数字","").toString();
            }
            if (!RegValidationUtil.validateIsNumber(String.valueOf(product.getInvestRule()))) {
                return FastJsonUtil.getResponse(Status.PARA_ERROR,"数量规则（投资金额按规则数的整倍数进行投资）不是数字","").toString();
            }
            if (RegValidationUtil.validateHasForbiddenMark(product.getProductName())) {
                return FastJsonUtil.getResponse(Status.PARA_ERROR,"产品名称含有非法字符","").toString();
            }

            Map rates = null;
            try {
                // 将请求参数转换成Map集合
                rates = FastJsonUtil.jsonToPojo(proEarningRates, Map.class);
                Set<String> keys = null;
                if (null != rates) {
                    keys = rates.keySet();
                    List<ProductEarningRate> list = new ArrayList<>();
                    // 封装利率实体集合
                    for (String k : keys) {
                        // 判断key是否是数字
                        if (!RegValidationUtil.validateIsNumber(k)) {
                            return FastJsonUtil.getResponse(Status.PARA_ERROR,"月份不是数字","").toString();
                        }
                        ProductEarningRate per = new ProductEarningRate();
                        per.setIncomeRate(Double.parseDouble(String.valueOf(rates.get(k))));
                        per.setMonth(Integer.valueOf(k));
                        // 封装理财产品的id到利率信息中
                        per.setProductId((int) product.getProId());
                        list.add(per);
                    }
                    // 封装利率信息到理财产品
                    product.setProEarningRate(list);
                } else {
                    return FastJsonUtil.error("参数错误：proEarningRate:" + proEarningRates);
                }
            } catch (Exception e1) {
                logger.error("系统异常：{}", e1);
                return FastJsonUtil.error("利率不符要求");
            }

            productService.updateProduct(product);
            return  FastJsonUtil.ok();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return FastJsonUtil.error(e.getMessage());
        }
    }
}
