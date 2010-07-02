package org.jahia.modules.filter;

import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.apache.commons.lang.StringUtils;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;

/**
 * SkinFilter
 *
 * Filter that add skins to the current resource.
 *
 * If a skin parameter is found, the filter just adds it to the wrappers list.
 *
 */
public class SkinFilter extends AbstractFilter {
    public String prepare(RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {
        String skin = resource.getNode().hasProperty("j:skin")?resource.getNode().getPropertyAsString("j:skin"):"";
        if (!StringUtils.isEmpty(skin) && !skin.equals("none")) {
            resource.pushWrapper(skin);
        }
        return null;
    }
}
