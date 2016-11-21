package observer.zagart.by.client.mvp.models.repository.entities;

import observer.zagart.by.client.application.interfaces.IConvertible;
import observer.zagart.by.client.application.interfaces.IExtractable;

/**
 * Interface contains methods and extends secondary interfaces
 * required for entities.
 *
 * @author zagart
 */
public interface IEntity<Entity, Target, PK> extends IConvertible<Target>, IExtractable<Entity> {

    Entity getNewEntity();

    PK getId();
}
