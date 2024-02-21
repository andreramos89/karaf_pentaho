/**
 *  Copyright 2014, Guillaume Nodet.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.ops4j.pax.url.mvn;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * A maven resolver service
 *
 * @author Guillaume Nodet
 * @author Grzegorz Grzybek
 */
public interface MavenResolver extends Closeable {

  /**
   * Resolve and download a maven based url
   */
  File resolve( String url ) throws IOException;

  /**
   * Resolve and download a maven based url - possibly as another attempt.
   * Specifying <code>previousException</code> is a hint to resolver.
   */
  File resolve( String url, Exception previousException ) throws IOException;

  /**
   * Resolve and download an artifact
   */
  File resolve( String groupId, String artifactId, String classifier,
                String extension, String version ) throws IOException;

  /**
   * Resolve and download an artifact - possibly as another attempt.
   * Specifying <code>previousException</code> is a hint to resolver.
   */
  File resolve( String groupId, String artifactId, String classifier,
                String extension, String version,
                Exception previousException ) throws IOException;

  /**
   * Resolve the maven metadata xml for the specified groupId:artifactId:version
   */
  File resolveMetadata( String groupId, String artifactId,
                        String type, String version ) throws IOException;

  /**
   * Resolve the maven metadata xml for the specified groupId:artifactId:version - possibly as another attempt.
   * Specifying <code>previousException</code> is a hint to resolver.
   */
  File resolveMetadata( String groupId, String artifactId,
                        String type, String version,
                        Exception previousException) throws IOException;

  /**
   * Install the specified artifact in the local repository
   */
  void upload( String groupId, String artifactId, String classifier,
               String extension, String version, File artifact ) throws IOException;

  /**
   * Install the specified artifact metadata in the local repository
   */

  void uploadMetadata( String groupId, String artifactId,
                       String type, String version, File artifact ) throws IOException;

  /**
   * Returns a hint about possible retry of operation that ended with <code>exception</code>
   * @param exception
   * @return
   */
  RetryChance isRetryableException(Exception exception);

  /**
   * Enumeration of retry hints that may be used by client code when trying to repeat failed resolution attempt
   */
  public enum RetryChance {
    NEVER(0),
    LOW(1),
    HIGH(2),
    UNKNOWN(Integer.MAX_VALUE);

    private int chance;

    RetryChance(int chance) {
      this.chance = chance;
    }

    /**
     * Ordering information for {@link RetryChance chances of retry}
     * @return
     */
    public int chance() {
      return chance;
    }
  }

}
