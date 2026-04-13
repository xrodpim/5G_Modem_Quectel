#!/usr/bin/env python3

import argparse
from datetime import datetime
import logging
import re
import subprocess
import sys

logging.basicConfig(
    level=logging.INFO,
    stream=sys.stdout,
    format="[%(asctime)s] %(levelname)8s: %(message)s"
)

PRIVATE_LOCAL_REGISTRY_URL='https://selfix.sboai.cs.eurecom.fr:443'

def main() -> None:
    args = _parse_args()

    cmd = f'curl --insecure -Ss -u oaicicd:oaicicd {PRIVATE_LOCAL_REGISTRY_URL}/v2/{args.repo_name}/tags/list | jq .'
    tagList = run_cmd(cmd)
    latestTag = ''
    latestDate = datetime.strptime('2022-01-01T00:00:00', '%Y-%m-%dT%H:%M:%S')
    for line in tagList.split('\n'):
        res = re.search('"(?P<tag>develop-[0-9a-zA-Z]+)"', line)
        if res is not None:
            tag = res.group('tag')
            if len(tag) == 15:
                cmd = f'curl --insecure -Ss -u oaicicd:oaicicd {PRIVATE_LOCAL_REGISTRY_URL}/v2/{args.repo_name}/manifests/{tag} | jq .history'
                tagInfo = run_cmd(cmd)
                res2 = re.search('"created.*(?P<date>202[0-9-]\-[0-9]+-[0-9]+T[0-9]+:[0-9]+:[0-9]+).*docker_version', tagInfo)
                if res2 is not None:
                    date = datetime.strptime(res2.group('date'), '%Y-%m-%dT%H:%M:%S')
                    if date > latestDate:
                        latestDate = date
                        latestTag = tag

    #logging.info(f'Latest Tag = {latestTag} made on {latestDate}')
    if latestTag == '':
        sys.exit(-1)
    print(latestTag)
    sys.exit(0)

def _parse_args() -> argparse.Namespace:
    """Parse the command line args

    Returns:
        argparse.Namespace: the created parser
    """
    parser = argparse.ArgumentParser(description='Script to retrieve on a given image repository, the latest (newest) develop image.')
    parser.add_argument(
        '--repo-name', '-rn',
        action='store',
        help='Image Repository Name (for example oai-spgwu-tiny)'
    )

    return parser.parse_args()

def run_cmd(cmd, silent=True):
    if not silent:
        logging.info(cmd)
    result = None
    try:
        res = subprocess.run(cmd,
                        shell=True, check=True,
                        stdout=subprocess.PIPE,
                        universal_newlines=True)
        result = res.stdout.strip()
    except:
        pass
    return result

if __name__ == '__main__':
    main()
